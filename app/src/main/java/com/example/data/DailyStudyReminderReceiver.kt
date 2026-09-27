package com.example.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DailyStudyReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action

        if (action == Intent.ACTION_BOOT_COMPLETED || action == Intent.ACTION_MY_PACKAGE_REPLACED) {
            // Re-schedule daily study reminder on device boot
            CoroutineScope(Dispatchers.IO).launch {
                val db = EnigmaDatabase.getDatabase(context)
                val activity = db.enigmaDao().getDailyActivityDirect()
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
            return
        }

        if (action == ACTION_TRIGGER_DAILY_STUDY_REMINDER || action == null) {
            CoroutineScope(Dispatchers.IO).launch {
                // 1. Check if user studied today and send notification if not
                DailyStudyNotificationManager.checkAndSendDailyStudyNotification(context)

                // 2. Re-schedule for tomorrow to ensure uninterrupted daily cycle
                val db = EnigmaDatabase.getDatabase(context)
                val activity = db.enigmaDao().getDailyActivityDirect()
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
        }
    }

    companion object {
        const val ACTION_TRIGGER_DAILY_STUDY_REMINDER = "com.example.ACTION_TRIGGER_DAILY_STUDY_REMINDER"
    }
}
