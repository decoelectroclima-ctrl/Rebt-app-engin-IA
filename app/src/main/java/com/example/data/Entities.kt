package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "module_progress")
data class ModuleProgressEntity(
    @PrimaryKey val moduleId: String,
    val answeredCount: Int,
    val correctCount: Int,
    val pct: Int,
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "exam_record")
data class ExamRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val moduleId: String,
    val correctCount: Int,
    val totalCount: Int,
    val pct: Int,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "daily_activity")
data class DailyActivityEntity(
    @PrimaryKey val id: Int = 1,
    val questionsAnswered: Int = 0,
    val calculatorsUsed: Int = 0,
    val schemasExplored: Int = 0,
    val streakDays: Int = 0,
    val lastActiveDate: String? = null, // YYYY-MM-DD
    val unlockedAchievements: String = "" // JSON or comma-separated list
)

@Entity(tableName = "subscription_record")
data class SubscriptionRecordEntity(
    @PrimaryKey val id: Int = 1,
    val plan: String = "gratuito", // "gratuito", "pro", "premium"
    val price: Double = 0.0,
    val transactionId: String? = null,
    val purchaseTime: Long = 0L,
    val isActive: Boolean = false
)

@Entity(tableName = "support_request")
data class SupportRequestEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val subject: String,
    val message: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "post_it")
data class PostItEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val content: String,
    val category: String = "General", // "Fórmula", "Artículo", "Consejo", "Examen"
    val color: String = "#FFEAA7", // Hex string
    val createdAt: Long = System.currentTimeMillis()
)

