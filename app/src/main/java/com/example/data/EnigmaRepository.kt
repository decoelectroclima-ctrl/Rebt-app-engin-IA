package com.example.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class EnigmaRepository(private val context: Context) {
    private val database = EnigmaDatabase.getDatabase(context)
    private val dao = database.enigmaDao()

    // 1. Progress State Flows
    val progressFlow: Flow<List<ModuleProgressEntity>> = dao.getModuleProgress()
    val examsHistoryFlow: Flow<List<ExamRecordEntity>> = dao.getExamRecords()
    val dailyActivityFlow: Flow<DailyActivityEntity?> = dao.getDailyActivity()
    val subscriptionFlow: Flow<SubscriptionRecordEntity?> = dao.getSubscriptionFlow()
    val supportRequestsFlow: Flow<List<SupportRequestEntity>> = dao.getSupportRequests()
    val postItsFlow: Flow<List<PostItEntity>> = dao.getPostIts()
    val customDocumentsFlow: Flow<List<CustomDocumentEntity>> = dao.getCustomDocuments()
    val customNewsFlow: Flow<List<CustomNewsEntity>> = dao.getCustomNews()
    val userLeadsFlow: Flow<List<UserLeadEntity>> = dao.getUserLeads()
    val questionReviewsFlow: Flow<List<QuestionReviewEntity>> = dao.getQuestionReviews()
    val remindersFlow: Flow<List<ReminderEntity>> = dao.getReminders()

    // 2. Action functions
    suspend fun saveModuleProgress(progress: ModuleProgressEntity) {
        dao.insertModuleProgress(progress)
    }

    suspend fun recordExam(moduleId: String, correct: Int, total: Int) {
        val pct = if (total > 0) (correct * 100 / total) else 0
        dao.insertExamRecord(
            ExamRecordEntity(
                moduleId = moduleId,
                correctCount = correct,
                totalCount = total,
                pct = pct
            )
        )
        
        // Create or update module entry
        dao.insertModuleProgress(
            ModuleProgressEntity(
                moduleId = moduleId,
                answeredCount = total,
                correctCount = correct,
                pct = pct
            )
        )

        // Increment daily stats
        incrementQuestionsCount(total)
    }

    suspend fun clearExamHistory() {
        dao.clearExamHistory()
    }

    suspend fun clearModuleProgress() {
        dao.clearModuleProgress()
    }

    suspend fun createSupportRequest(name: String, email: String, subject: String, message: String) {
        dao.insertSupportRequest(
            SupportRequestEntity(
                name = name,
                email = email,
                subject = subject,
                message = message
            )
        )
    }

    suspend fun insertPostIt(content: String, category: String, color: String) {
        dao.insertPostIt(
            PostItEntity(
                content = content,
                category = category,
                color = color
            )
        )
    }

    suspend fun deletePostIt(id: Int) {
        dao.deletePostItById(id)
    }

    suspend fun useCalculator() {
        val activity = dao.getDailyActivityDirect() ?: DailyActivityEntity(
            streakDays = 1,
            lastActiveDate = getTodayDateString()
        )
        dao.insertDailyActivity(activity.copy(calculatorsUsed = activity.calculatorsUsed + 1))
    }

    suspend fun exploreSchema() {
        val activity = dao.getDailyActivityDirect() ?: DailyActivityEntity(
            streakDays = 1,
            lastActiveDate = getTodayDateString()
        )
        dao.insertDailyActivity(activity.copy(schemasExplored = activity.schemasExplored + 1))
    }

    suspend fun recordItcStudy(itcCode: String) {
        val today = getTodayDateString()
        val activity = dao.getDailyActivityDirect() ?: DailyActivityEntity(
            streakDays = 1,
            lastActiveDate = today,
            studiedItcsToday = itcCode,
            lastStudyTimestamp = System.currentTimeMillis()
        )

        var currentStreak = activity.streakDays
        val updatedStudiedItcs = if (activity.lastActiveDate == today) {
            val existing = activity.studiedItcsToday.split(",").map { it.trim() }.filter { it.isNotEmpty() }.toMutableSet()
            existing.add(itcCode)
            existing.joinToString(", ")
        } else {
            currentStreak = if (activity.lastActiveDate == getYesterdayDateString()) currentStreak + 1 else 1
            itcCode
        }

        val updatedActivity = activity.copy(
            lastActiveDate = today,
            streakDays = currentStreak,
            studiedItcsToday = updatedStudiedItcs,
            lastStudyTimestamp = System.currentTimeMillis()
        )
        dao.insertDailyActivity(updatedActivity)
    }

    suspend fun updateDailyStudyReminderSettings(enabled: Boolean, hour: Int, minute: Int) {
        val activity = dao.getDailyActivityDirect() ?: DailyActivityEntity(
            streakDays = 0,
            lastActiveDate = getTodayDateString()
        )
        val updated = activity.copy(
            dailyStudyReminderEnabled = enabled,
            dailyStudyReminderHour = hour,
            dailyStudyReminderMinute = minute
        )
        dao.insertDailyActivity(updated)
        DailyStudyNotificationManager.scheduleDailyStudyAlarm(
            context = context,
            hour = hour,
            minute = minute,
            enabled = enabled
        )
    }

    suspend fun updateDailyActivity(activity: DailyActivityEntity) {
        dao.insertDailyActivity(activity)
    }

    suspend fun initDailyStudyReminder() {
        DailyStudyNotificationManager.ensureDailyStudyChannel(context)
        val activity = dao.getDailyActivityDirect()
        val enabled = activity?.dailyStudyReminderEnabled ?: true
        val hour = activity?.dailyStudyReminderHour ?: 20
        val minute = activity?.dailyStudyReminderMinute ?: 0

        DailyStudyNotificationManager.scheduleDailyStudyAlarm(
            context = context,
            hour = hour,
            minute = minute,
            enabled = enabled
        )
    }

    private suspend fun incrementQuestionsCount(count: Int) {
        val activity = dao.getDailyActivityDirect() ?: DailyActivityEntity(
            streakDays = 1,
            lastActiveDate = getTodayDateString()
        )
        val today = getTodayDateString()
        var currentStreak = activity.streakDays
        if (activity.lastActiveDate != today) {
            currentStreak = if (activity.lastActiveDate == getYesterdayDateString()) currentStreak + 1 else 1
        }
        
        dao.insertDailyActivity(
            activity.copy(
                questionsAnswered = activity.questionsAnswered + count,
                streakDays = currentStreak,
                lastActiveDate = today,
                lastStudyTimestamp = System.currentTimeMillis()
            )
        )
    }

    // Google Play Billing local record stores
    suspend fun activatePremiumSubscription(plan: String, price: Double, transactionId: String? = null, purchaseTime: Long? = null) {
        val currentSubscription = dao.getSubscriptionDirect()
        
        // If already active with the same transactionId, do nothing (to avoid re-writing)
        if (currentSubscription != null && currentSubscription.isActive && currentSubscription.transactionId == transactionId) {
            return
        }

        val finalTransactionId = transactionId ?: ("GPA." + (1000..9999).random() + "-" + (1000..9999).random() + "-" + (1000..9999).random())
        val finalPurchaseTime = purchaseTime ?: System.currentTimeMillis()
        
        dao.insertSubscription(
            SubscriptionRecordEntity(
                plan = plan,
                price = price,
                transactionId = finalTransactionId,
                purchaseTime = finalPurchaseTime,
                isActive = true
            )
        )
    }

    suspend fun restoreOrCancelSubscription() {
        dao.insertSubscription(
            SubscriptionRecordEntity(
                plan = "gratuito",
                price = 0.0,
                transactionId = null,
                purchaseTime = 0L,
                isActive = false
            )
        )
    }

    // Helper Date functions
    private fun getTodayDateString(): String {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        return sdf.format(java.util.Date())
    }

    private fun getYesterdayDateString(): String {
        val cal = java.util.Calendar.getInstance()
        cal.add(java.util.Calendar.DATE, -1)
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        return sdf.format(cal.time)
    }

    // New methods for Custom Documents and News
    suspend fun insertCustomDocument(document: CustomDocumentEntity) {
        dao.insertCustomDocument(document)
    }

    suspend fun deleteCustomDocument(docId: String) {
        dao.deleteCustomDocumentById(docId)
    }

    suspend fun clearCustomDocuments() {
        dao.clearCustomDocuments()
    }

    suspend fun insertCustomNews(news: CustomNewsEntity) {
        dao.insertCustomNews(news)
    }

    suspend fun deleteCustomNews(newsId: String) {
        dao.deleteCustomNewsById(newsId)
    }

    suspend fun clearCustomNews() {
        dao.clearCustomNews()
    }

    suspend fun insertUserLead(lead: UserLeadEntity) {
        dao.insertUserLead(lead)
    }

    suspend fun updateUserLead(lead: UserLeadEntity) {
        dao.updateUserLead(lead)
    }

    suspend fun deleteUserLead(id: Int) {
        dao.deleteUserLeadById(id)
    }

    suspend fun clearUserLeads() {
        dao.clearUserLeads()
    }

    suspend fun resetDailyActivity() {
        dao.insertDailyActivity(
            DailyActivityEntity(
                id = 1,
                questionsAnswered = 0,
                calculatorsUsed = 0,
                schemasExplored = 0,
                streakDays = 0,
                lastActiveDate = null,
                unlockedAchievements = ""
            )
        )
    }

    suspend fun recordQuestionMistake(
        questionText: String,
        moduleKey: String,
        selectedOption: Int,
        correctOption: Int,
        explanation: String,
        reference: String
    ) {
        val qId = "${moduleKey}_${questionText.hashCode()}"
        dao.insertQuestionReview(
            QuestionReviewEntity(
                questionId = qId,
                questionText = questionText,
                moduleKey = moduleKey,
                selectedOption = selectedOption,
                correctOption = correctOption,
                explanation = explanation,
                reference = reference,
                failCount = 1,
                isMastered = false,
                lastReviewedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun markQuestionMastered(questionId: String) {
        dao.markQuestionMastered(questionId)
    }

    suspend fun clearQuestionReviews() {
        dao.clearQuestionReviews()
    }

    // -------------------------------------------------------------
    // REMINDERS (SISTEMA DE RECORDATORIOS REBT)
    // -------------------------------------------------------------

    suspend fun insertReminder(reminder: ReminderEntity): Long {
        val id = dao.insertReminder(reminder)
        val savedReminder = reminder.copy(id = id.toInt())
        ReminderNotificationManager.scheduleReminderAlarm(context, savedReminder)
        return id
    }

    suspend fun updateReminder(reminder: ReminderEntity) {
        dao.updateReminder(reminder)
        ReminderNotificationManager.scheduleReminderAlarm(context, reminder)
    }

    suspend fun deleteReminder(id: Int) {
        ReminderNotificationManager.cancelReminderAlarm(context, id)
        dao.deleteReminderById(id)
    }

    suspend fun toggleReminderStatus(reminder: ReminderEntity) {
        if (reminder.status == "Completado") {
            // Revert back to Pendiente
            val newStatus = if (reminder.dueDate < System.currentTimeMillis()) "Vencido" else "Pendiente"
            val updated = reminder.copy(status = newStatus, completedAt = null)
            dao.updateReminder(updated)
            ReminderNotificationManager.scheduleReminderAlarm(context, updated)
        } else {
            // Mark as Completado
            if (reminder.periodicity != "Puntual") {
                // If periodic, calculate next due date and keep active or advance date
                val nextDate = ReminderNotificationManager.calculateNextDueDate(reminder.dueDate, reminder.periodicity)
                val updated = reminder.copy(
                    dueDate = nextDate,
                    status = "Pendiente",
                    completedAt = System.currentTimeMillis()
                )
                dao.updateReminder(updated)
                ReminderNotificationManager.scheduleReminderAlarm(context, updated)
            } else {
                val updated = reminder.copy(
                    status = "Completado",
                    completedAt = System.currentTimeMillis()
                )
                dao.updateReminder(updated)
                ReminderNotificationManager.cancelReminderAlarm(context, reminder.id)
            }
        }
    }

    suspend fun seedDefaultRemindersIfEmpty() {
        val list = dao.getReminderById(1)
        // If no reminder with ID 1 exists, let's seed official template reminders
        val defaultReminders = listOf(
            ReminderEntity(
                title = "Inspección Periódica OCA (Pública Concurrencia)",
                description = "Revisión quinquenal obligatoria por Organismo de Control Autorizado para locales de pública concurrencia (aforo > 100 personas).",
                rebtArticle = "ITC-BT-05 / ITC-BT-28",
                dueDate = System.currentTimeMillis() + (3 * 86400000L), // In 3 days
                periodicity = "Anual",
                priority = "Alta",
                status = "Pendiente",
                notifyEnabled = true
            ),
            ReminderEntity(
                title = "Comprobación Anual Resistencia de Puesta a Tierra",
                description = "Medición del valor óhmico en electrodos de tierra y revisión de continuidad de conductores de protección en época más seca.",
                rebtArticle = "ITC-BT-18 (Puesta a Tierra)",
                dueDate = System.currentTimeMillis() + (7 * 86400000L), // In 7 days
                periodicity = "Anual",
                priority = "Alta",
                status = "Pendiente",
                notifyEnabled = true
            ),
            ReminderEntity(
                title = "Test Semestral de Interruptores Diferenciales (ID)",
                description = "Comprobación del pulsador de prueba (test) y verificación de disparo a corriente residual nominal (≤ 30 mA).",
                rebtArticle = "ITC-BT-24 (Protecciones)",
                dueDate = System.currentTimeMillis() + (14 * 86400000L), // In 14 days
                periodicity = "Semanal",
                priority = "Media",
                status = "Pendiente",
                notifyEnabled = true
            ),
            ReminderEntity(
                title = "Revisión Alumbrado de Emergencia y Autonomía",
                description = "Corte de suministro voluntario para verificar 1 hora mínima de autonomía lumínica y señalización de evacuación.",
                rebtArticle = "ITC-BT-28 (Emergencia)",
                dueDate = System.currentTimeMillis() - (2 * 86400000L), // Expired 2 days ago to demonstrate vencido state
                periodicity = "Mensual",
                priority = "Media",
                status = "Vencido",
                notifyEnabled = true
            ),
            ReminderEntity(
                title = "Renovación Póliza Responsabilidad Civil Instalador",
                description = "Mantener en vigor la póliza de seguro de RC por un mínimo de 600.000€ / 900.000€ según categoría básica o especialista.",
                rebtArticle = "Art. 22 (Empresas Instaladoras)",
                dueDate = System.currentTimeMillis() + (30 * 86400000L), // In 30 days
                periodicity = "Anual",
                priority = "Alta",
                status = "Pendiente",
                notifyEnabled = true
            )
        )

        for (item in defaultReminders) {
            insertReminder(item)
        }
    }
}
