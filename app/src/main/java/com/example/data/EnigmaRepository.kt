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
    val studentEventsFlow: Flow<List<StudentCalendarEventEntity>> = dao.getStudentCalendarEvents()
    val studyPlanFlow: Flow<StudyPlanEntity?> = dao.getStudyPlanFlow()

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

    // --- Student Calendar & Study Plan Methods ---

    suspend fun insertStudentEvent(event: StudentCalendarEventEntity): Long {
        return dao.insertStudentCalendarEvent(event)
    }

    suspend fun updateStudentEvent(event: StudentCalendarEventEntity) {
        dao.updateStudentCalendarEvent(event)
    }

    suspend fun toggleStudentEventCompletion(id: Int, isCompleted: Boolean) {
        dao.updateStudentCalendarEventCompletion(id, isCompleted)
    }

    suspend fun deleteStudentEvent(id: Int) {
        dao.deleteStudentCalendarEventById(id)
    }

    suspend fun clearAllStudentEvents() {
        dao.clearAllStudentCalendarEvents()
    }

    suspend fun saveStudyPlan(plan: StudyPlanEntity) {
        dao.insertStudyPlan(plan)
    }

    suspend fun getStudyPlanDirect(): StudyPlanEntity? {
        return dao.getStudyPlanDirect()
    }

    private fun getOffsetDate(days: Int): String {
        val cal = java.util.Calendar.getInstance()
        cal.add(java.util.Calendar.DAY_OF_YEAR, days)
        return java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(cal.time)
    }

    suspend fun initializeDefaultCalendarAndPlan() {
        val theoryExamDate = getOffsetDate(28)
        val practiceExamDate = getOffsetDate(35)

        val plan = StudyPlanEntity(
            id = 1,
            targetExamDate = theoryExamDate,
            targetPracticeExamDate = practiceExamDate,
            examCallName = "Convocatoria Oficial Instalador REBT 2026",
            studyPlanMode = "ESTANDAR",
            hoursPerWeek = 8,
            notes = "Plan estructurado con clases teóricas, talleres de verificación y montaje, y simulacros cronometrados.",
            isCustomized = false,
            lastUpdated = System.currentTimeMillis()
        )
        dao.insertStudyPlan(plan)

        val initialEvents = listOf(
            StudentCalendarEventEntity(
                title = "Clase Teórica: ITC-BT-18 y Puesta a Tierra",
                description = "Esquemas de conexión TN, TT e IT, electrodos enterrados, medición de resistividad y conductores equipotenciales.",
                date = getOffsetDate(2),
                time = "18:00",
                durationMinutes = 90,
                eventType = "CLASS_THEORY",
                relatedItc = "ITC-BT-18",
                locationOrNotes = "Aula Virtual / Centro de Formación",
                colorHex = "#58A6FF"
            ),
            StudentCalendarEventEntity(
                title = "Clase Práctica / Taller: Medición con Telurómetro y Bucle",
                description = "Uso práctico del telurómetro (método 3 picas), impedancia de bucle de defecto y comprobación de disparo de ID a 30mA.",
                date = getOffsetDate(5),
                time = "16:30",
                durationMinutes = 120,
                eventType = "CLASS_PRACTICE",
                relatedItc = "Verificaciones ITC-05",
                locationOrNotes = "Taller Eléctrico - Banco de Ensayos",
                colorHex = "#BC8CFF"
            ),
            StudentCalendarEventEntity(
                title = "Estudio Programado: ITC-BT-19 a 27 (Instalaciones Interiores)",
                description = "Dimensionamiento de circuitos C1 a C5 en viviendas, secciones mínimas, tubos protectores y caídas de tensión (1% / 3%).",
                date = getOffsetDate(8),
                time = "19:00",
                durationMinutes = 60,
                eventType = "STUDY_SESSION",
                relatedItc = "ITC-BT-19 a 27",
                locationOrNotes = "Estudio Personal con app EnginIA",
                colorHex = "#F5B041"
            ),
            StudentCalendarEventEntity(
                title = "Clase Teórica: Previsión de Cargas y Locales Especiales",
                description = "ITC-BT-10 e ITC-BT-28 (Pública concurrencia: aforo >100 personas, suministro de socorro, cables libres de halógenos).",
                date = getOffsetDate(12),
                time = "18:00",
                durationMinutes = 90,
                eventType = "CLASS_THEORY",
                relatedItc = "ITC-BT-10 / 28",
                locationOrNotes = "Aula Virtual / Centro de Formación",
                colorHex = "#58A6FF"
            ),
            StudentCalendarEventEntity(
                title = "Clase Práctica / Taller: Montaje de Cuadros y Protecciones",
                description = "Montaje real de IGA, protector de sobretensiones transitorias y permanentes, diferenciales tipo A y PIAs.",
                date = getOffsetDate(16),
                time = "16:00",
                durationMinutes = 120,
                eventType = "CLASS_PRACTICE",
                relatedItc = "ITC-BT-17 / 24",
                locationOrNotes = "Taller de Cuadros Eléctricos",
                colorHex = "#BC8CFF"
            ),
            StudentCalendarEventEntity(
                title = "Simulacro Oficial de Examen Teórico (40 Preguntas)",
                description = "Simulacro cronometrado con el banco oficial de preguntas de la Junta/Comunidad. 90 minutos para evaluar umbral del 75%.",
                date = getOffsetDate(21),
                time = "10:00",
                durationMinutes = 90,
                eventType = "SIMULATION",
                relatedItc = "Simulacro Global",
                locationOrNotes = "App EnginIA REBT - Modo Examen",
                colorHex = "#E67E22"
            ),
            StudentCalendarEventEntity(
                title = "Repaso Intensivo y Resolución de Dudas de Normativa",
                description = "Aclaración de preguntas trampa de exámenes anteriores y fórmulas de cálculo de Iz y caída de tensión.",
                date = getOffsetDate(25),
                time = "18:30",
                durationMinutes = 90,
                eventType = "CLASS_THEORY",
                relatedItc = "Repaso General",
                locationOrNotes = "Sesión de Dudas y Tutoría",
                colorHex = "#58A6FF"
            ),
            StudentCalendarEventEntity(
                title = "⭐ DÍA DEL EXAMEN TEÓRICO OFICIAL REBT",
                description = "Examen Oficial de Instalador Autorizado en Baja Tensión. Llevar DNI original, bolígrafo azul/negro, calculadora y reglamento REBT sin anotaciones.",
                date = theoryExamDate,
                time = "09:30",
                durationMinutes = 90,
                eventType = "EXAM_THEORY",
                relatedItc = "Examen Oficial",
                locationOrNotes = "Sede Oficial de Exámenes / Delegación de Industria",
                colorHex = "#F85149"
            ),
            StudentCalendarEventEntity(
                title = "Taller Práctico Final: Ensayo Previo de la Prueba Práctica",
                description = "Simulación contrarreloj de la prueba de taller: conexionado de conmutadas, medida de aislamiento con megóhmetro (500V, >0.5MΩ).",
                date = getOffsetDate(31),
                time = "16:00",
                durationMinutes = 120,
                eventType = "CLASS_PRACTICE",
                relatedItc = "Taller Práctico",
                locationOrNotes = "Taller de Pruebas Oficiales",
                colorHex = "#BC8CFF"
            ),
            StudentCalendarEventEntity(
                title = "🛠️ DÍA DEL EXAMEN PRÁCTICO OFICIAL",
                description = "Prueba Práctica Oficial: Montaje de circuito de alumbrado/fuerza, conexionado de cuadro y batería de mediciones con instrumentos reglamentarios.",
                date = practiceExamDate,
                time = "09:00",
                durationMinutes = 180,
                eventType = "EXAM_PRACTICE",
                relatedItc = "Examen Práctico Oficial",
                locationOrNotes = "Sede de Talleres Oficiales de Industria",
                colorHex = "#3FB950"
            )
        )

        dao.insertStudentCalendarEvents(initialEvents)
    }

    suspend fun generateAutomatedStudyPlan(
        targetTheoryDate: String,
        targetPracticeDate: String,
        planMode: String,
        callName: String
    ) {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        val today = java.util.Calendar.getInstance()
        val theoryCal = java.util.Calendar.getInstance()
        try {
            sdf.parse(targetTheoryDate)?.let { theoryCal.time = it }
        } catch (_: Exception) {}

        val diffDays = ((theoryCal.timeInMillis - today.timeInMillis) / (1000 * 60 * 60 * 24)).toInt().coerceAtLeast(7)

        val updatedPlan = StudyPlanEntity(
            id = 1,
            targetExamDate = targetTheoryDate,
            targetPracticeExamDate = targetPracticeDate,
            examCallName = callName.ifBlank { "Convocatoria Oficial Instalador REBT 2026" },
            studyPlanMode = planMode,
            hoursPerWeek = when (planMode) {
                "INTENSIVO" -> 15
                "EXTENDIDO" -> 4
                else -> 8
            },
            notes = "Plan optimizado de $diffDays días con distribución de ITCs, prácticas de taller y simulacros oficiales.",
            isCustomized = true,
            lastUpdated = System.currentTimeMillis()
        )
        dao.insertStudyPlan(updatedPlan)

        // Clear existing generated study sessions and classes so we don't duplicate
        dao.clearGeneratedStudyPlanEvents()

        val generatedEvents = mutableListOf<StudentCalendarEventEntity>()

        // 1. Ensure the Theoretical Exam event is present
        generatedEvents.add(
            StudentCalendarEventEntity(
                title = "⭐ DÍA DEL EXAMEN TEÓRICO OFICIAL",
                description = "Prueba Teórica Oficial de Instalador en Baja Tensión. Llevar REBT, DNI y calculadora homologada.",
                date = targetTheoryDate,
                time = "09:30",
                durationMinutes = 90,
                eventType = "EXAM_THEORY",
                relatedItc = "Examen Oficial",
                locationOrNotes = "Sede Oficial Convocatoria Industria",
                colorHex = "#F85149"
            )
        )

        // 2. Ensure Practical Exam event is present
        generatedEvents.add(
            StudentCalendarEventEntity(
                title = "🛠️ DÍA DEL EXAMEN PRÁCTICO OFICIAL",
                description = "Prueba Práctica Oficial: Montaje, esquemas de cuadro, verificación y medidas de seguridad (ITC-03/04/05/18/24).",
                date = targetPracticeDate,
                time = "09:00",
                durationMinutes = 180,
                eventType = "EXAM_PRACTICE",
                relatedItc = "Examen Práctico Oficial",
                locationOrNotes = "Talleres Homologados de Certificación",
                colorHex = "#3FB950"
            )
        )

        // 3. Generate milestone events spread evenly between today and exam dates
        val modulesSchedule = listOf(
            Triple("Clase Teórica: Articulado y Documentación Técnica (ITC-01 a 05)", "Conceptos básicos, proyectos, memorias técnicas de diseño MTD y tramitación administrativa.", "ITC-BT-01 a 05"),
            Triple("Clase Teórica: Redes de Distribución y Enlace (ITC-06 a 17)", "Aéreas, subterráneas, cajas generales de protección CGP, LGA y centralización de contadores.", "ITC-BT-10 a 16"),
            Triple("Clase Práctica / Taller: Medidas de Tierra e Instrumentos", "Ensayo práctico con telurómetro y comprobador de aislamiento (500V).", "ITC-BT-18"),
            Triple("Clase Teórica: Instalaciones Interiores y Previsión (ITC-19 a 27)", "Cálculo de intensidades admisibles, caída de tensión y número de circuitos obligatorios.", "ITC-BT-19 a 27"),
            Triple("Clase Práctica / Taller: Montaje de Cuadros y Protecciones", "Cableado de ICP/IGA, protector de sobretensiones, diferenciales y térmicos con peines de conexión.", "ITC-BT-24"),
            Triple("Clase Teórica: Locales Especiales (ITC-28 a 33)", "Pública concurrencia, locales mojados, húmedos, con riesgo de incendio y atmósferas explosivas.", "ITC-BT-28 a 33"),
            Triple("Simulacro Oficial de Examen: 40 Preguntas Cronometradas", "Evaluación de tiempo de respuesta y análisis de fallos en la app.", "Simulacro Global"),
            Triple("Clase Teórica: Instalaciones con Fines Especiales (ITC-34 a 52)", "Piscinas, recarga de vehículo eléctrico (ITC-52), generadores y quirófanos.", "ITC-BT-52"),
            Triple("Clase Práctica / Taller: Ensayo de Examen Práctico Cronometrado", "Simulación completa del ejercicio práctico con tiempo limitado y rúbrica de evaluación.", "Taller Final")
        )

        val totalMilestones = modulesSchedule.size
        for (i in 0 until totalMilestones) {
            val fraction = (i + 1).toFloat() / (totalMilestones + 1)
            val dayOffset = (diffDays * fraction).toInt().coerceIn(1, diffDays - 1)
            val eventDate = getOffsetDate(dayOffset)
            val item = modulesSchedule[i]
            val isPractice = item.first.contains("Práctica", ignoreCase = true) || item.first.contains("Taller", ignoreCase = true)
            val isSimulation = item.first.contains("Simulacro", ignoreCase = true)

            val type = when {
                isSimulation -> "SIMULATION"
                isPractice -> "CLASS_PRACTICE"
                else -> "CLASS_THEORY"
            }
            val color = when {
                isSimulation -> "#E67E22"
                isPractice -> "#BC8CFF"
                else -> "#58A6FF"
            }

            generatedEvents.add(
                StudentCalendarEventEntity(
                    title = item.first,
                    description = item.second,
                    date = eventDate,
                    time = if (isPractice) "16:30" else "18:30",
                    durationMinutes = if (isPractice) 120 else 90,
                    eventType = type,
                    relatedItc = item.third,
                    locationOrNotes = if (isPractice) "Taller Eléctrico y Banco de Medidas" else "Aula Virtual / App EnginIA",
                    colorHex = color
                )
            )

            // Add an intermediate study session day
            if (dayOffset + 1 < diffDays) {
                generatedEvents.add(
                    StudentCalendarEventEntity(
                        title = "Estudio y Test de Repaso: ${item.third}",
                        description = "Realizar 20 preguntas del test temático y consolidar fórmulas en el Laboratorio de Cálculo.",
                        date = getOffsetDate(dayOffset + 1),
                        time = "19:30",
                        durationMinutes = 45,
                        eventType = "STUDY_SESSION",
                        relatedItc = item.third,
                        locationOrNotes = "Estudio Personal con app EnginIA",
                        colorHex = "#F5B041"
                    )
                )
            }
        }

        dao.insertStudentCalendarEvents(generatedEvents)
    }
}
