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
}
