package com.example.data

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import com.example.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReminderNotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getIntExtra(EXTRA_REMINDER_ID, 0)
        val title = intent.getStringExtra(EXTRA_TITLE) ?: "Recordatorio REBT"
        val description = intent.getStringExtra(EXTRA_DESCRIPTION) ?: "Tienes una tarea o revisión pendiente del Reglamento."
        val article = intent.getStringExtra(EXTRA_ARTICLE) ?: "REBT"
        val action = intent.action

        if (action == ACTION_MARK_COMPLETED && reminderId != 0) {
            // User tapped "Marcar como Completado" action button on the notification
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(reminderId)

            CoroutineScope(Dispatchers.IO).launch {
                val db = EnigmaDatabase.getDatabase(context)
                val reminder = db.enigmaDao().getReminderById(reminderId)
                if (reminder != null) {
                    val repo = EnigmaRepository(context)
                    repo.toggleReminderStatus(reminder)
                }
            }
            return
        }

        // Standard alarm trigger: show the notification
        showNotification(context, reminderId, title, description, article)
    }

    private fun showNotification(
        context: Context,
        reminderId: Int,
        title: String,
        description: String,
        article: String
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        ReminderNotificationManager.ensureNotificationChannel(context)

        // Main Tap Intent -> Open app
        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("navigate_to", "reminders")
        }
        val pendingOpenIntent = PendingIntent.getActivity(
            context,
            reminderId,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Action Intent -> Mark as complete
        val markCompletedIntent = Intent(context, ReminderNotificationReceiver::class.java).apply {
            this.action = ACTION_MARK_COMPLETED
            putExtra(EXTRA_REMINDER_ID, reminderId)
        }
        val pendingMarkIntent = PendingIntent.getBroadcast(
            context,
            reminderId + 100000,
            markCompletedIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, ReminderNotificationManager.CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setContentTitle("⚡ $title ($article)")
            .setContentText(description)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("$description\n\n📌 Norma asociada: $article\nCumple con la periodicidad reglamentaria del REBT.")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingOpenIntent)
            .addAction(android.R.drawable.checkbox_on_background, "Marcar Completado", pendingMarkIntent)
            .build()

        notificationManager.notify(if (reminderId != 0) reminderId else (System.currentTimeMillis() % 10000).toInt(), notification)
    }

    companion object {
        const val EXTRA_REMINDER_ID = "extra_reminder_id"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_ARTICLE = "extra_article"
        const val ACTION_MARK_COMPLETED = "com.example.ACTION_MARK_REMINDER_COMPLETED"
    }
}
