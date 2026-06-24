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
                lastActiveDate = today
            )
        )
    }

    // Google Play Billing local record stores
    suspend fun activatePremiumSubscription(plan: String, price: Double) {
        val txId = "GPA." + (1000..9999).random() + "-" + (1000..9999).random() + "-" + (1000..9999).random()
        dao.insertSubscription(
            SubscriptionRecordEntity(
                plan = plan,
                price = price,
                transactionId = txId,
                purchaseTime = System.currentTimeMillis(),
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
}
