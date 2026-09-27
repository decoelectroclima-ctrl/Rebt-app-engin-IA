package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        ModuleProgressEntity::class,
        ExamRecordEntity::class,
        DailyActivityEntity::class,
        SubscriptionRecordEntity::class,
        SupportRequestEntity::class,
        PostItEntity::class,
        CustomDocumentEntity::class,
        CustomNewsEntity::class,
        UserLeadEntity::class,
        QuestionReviewEntity::class,
        ReminderEntity::class
    ],
    version = 5,
    exportSchema = false
)
abstract class EnigmaDatabase : RoomDatabase() {
    abstract fun enigmaDao(): EnigmaDao

    companion object {
        @Volatile
        private var INSTANCE: EnigmaDatabase? = null

        fun getDatabase(context: Context): EnigmaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EnigmaDatabase::class.java,
                    "enigma_rebt_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
