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

@Entity(tableName = "custom_document")
data class CustomDocumentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val docId: String, // Unique identifier e.g., "boe_rebt"
    val title: String,
    val description: String,
    val fileName: String,
    val fileSize: String,
    val type: String, // "BOE", "Esquema", "Calculadora"
    val isCustom: Boolean = true,
    val uriString: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "custom_news")
data class CustomNewsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val newsId: String,
    val title: String,
    val summary: String,
    val content: String,
    val date: String,
    val category: String, // "borrador", "ev", "autoconsumo", "inspecciones"
    val categoryLabel: String,
    val readTime: String = "3 min",
    val hot: Boolean = false,
    val isCustom: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_lead")
data class UserLeadEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val email: String,
    val name: String,
    val subscriptionPlan: String, // "gratuito", "pro", "premium"
    val isActive: Boolean = true,
    val phoneNumber: String,
    val companyName: String,
    val province: String,
    val isSold: Boolean = false,
    val leadPrice: Double = 35.00,
    val registrationDate: Long = System.currentTimeMillis()
)

@Entity(tableName = "question_review")
data class QuestionReviewEntity(
    @PrimaryKey val questionId: String,
    val questionText: String,
    val moduleKey: String,
    val selectedOption: Int,
    val correctOption: Int,
    val explanation: String,
    val reference: String,
    val failCount: Int = 1,
    val isMastered: Boolean = false,
    val lastReviewedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "rebt_reminder")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val rebtArticle: String = "ITC-BT-05",
    val dueDate: Long = System.currentTimeMillis() + 86400000L, // timestamp
    val periodicity: String = "Puntual", // "Puntual", "Diaria", "Semanal", "Mensual", "Anual"
    val priority: String = "Media", // "Baja", "Media", "Alta"
    val status: String = "Pendiente", // "Pendiente", "Completado", "Vencido"
    val notifyEnabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null
)



