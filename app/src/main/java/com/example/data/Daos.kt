package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EnigmaDao {
    // Progress
    @Query("SELECT * FROM module_progress")
    fun getModuleProgress(): Flow<List<ModuleProgressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertModuleProgress(progress: ModuleProgressEntity)

    @Query("DELETE FROM module_progress")
    suspend fun clearModuleProgress()

    // Exams
    @Query("SELECT * FROM exam_record ORDER BY createdAt DESC")
    fun getExamRecords(): Flow<List<ExamRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExamRecord(record: ExamRecordEntity)

    @Query("DELETE FROM exam_record")
    suspend fun clearExamHistory()

    // Daily Activity
    @Query("SELECT * FROM daily_activity WHERE id = 1 LIMIT 1")
    fun getDailyActivity(): Flow<DailyActivityEntity?>

    @Query("SELECT * FROM daily_activity WHERE id = 1 LIMIT 1")
    suspend fun getDailyActivityDirect(): DailyActivityEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyActivity(activity: DailyActivityEntity)

    // Subscription
    @Query("SELECT * FROM subscription_record WHERE id = 1 LIMIT 1")
    fun getSubscriptionFlow(): Flow<SubscriptionRecordEntity?>

    @Query("SELECT * FROM subscription_record WHERE id = 1 LIMIT 1")
    suspend fun getSubscriptionDirect(): SubscriptionRecordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubscription(subscription: SubscriptionRecordEntity)

    // Support Requests
    @Query("SELECT * FROM support_request ORDER BY createdAt DESC")
    fun getSupportRequests(): Flow<List<SupportRequestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupportRequest(request: SupportRequestEntity)

    // Post-its
    @Query("SELECT * FROM post_it ORDER BY createdAt DESC")
    fun getPostIts(): Flow<List<PostItEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPostIt(postIt: PostItEntity)

    @Query("DELETE FROM post_it WHERE id = :id")
    suspend fun deletePostItById(id: Int)

    // Custom Documents (PDFs)
    @Query("SELECT * FROM custom_document ORDER BY createdAt DESC")
    fun getCustomDocuments(): Flow<List<CustomDocumentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomDocument(document: CustomDocumentEntity)

    @Query("DELETE FROM custom_document WHERE docId = :docId")
    suspend fun deleteCustomDocumentById(docId: String)

    @Query("DELETE FROM custom_document")
    suspend fun clearCustomDocuments()

    // Custom News (Real-time centered on REBT 2026)
    @Query("SELECT * FROM custom_news ORDER BY createdAt DESC")
    fun getCustomNews(): Flow<List<CustomNewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomNews(news: CustomNewsEntity)

    @Query("DELETE FROM custom_news WHERE newsId = :newsId")
    suspend fun deleteCustomNewsById(newsId: String)

    @Query("DELETE FROM custom_news")
    suspend fun clearCustomNews()

    // User Leads
    @Query("SELECT * FROM user_lead ORDER BY registrationDate DESC")
    fun getUserLeads(): Flow<List<UserLeadEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserLead(lead: UserLeadEntity)

    @Update
    suspend fun updateUserLead(lead: UserLeadEntity)

    @Query("DELETE FROM user_lead WHERE id = :leadId")
    suspend fun deleteUserLeadById(leadId: Int)

    @Query("DELETE FROM user_lead")
    suspend fun clearUserLeads()
}
