package com.example.data

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.MainActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object ReminderNotificationManager {
    const val CHANNEL_ID = "rebt_reminders_channel"
    private const val CHANNEL_NAME = "Recordatorios REBT"
    private const val CHANNEL_DESCRIPTION = "Avisos de inspecciones periódicas, mantenimiento e hitos normativos del REBT"

    fun ensureNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
                enableVibration(true)
                enableLights(true)
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleReminderAlarm(context: Context, reminder: ReminderEntity) {
        if (!reminder.notifyEnabled || reminder.status == "Completado") {
            cancelReminderAlarm(context, reminder.id)
            return
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val intent = Intent(context, ReminderNotificationReceiver::class.java).apply {
            putExtra(ReminderNotificationReceiver.EXTRA_REMINDER_ID, reminder.id)
            putExtra(ReminderNotificationReceiver.EXTRA_TITLE, reminder.title)
            putExtra(ReminderNotificationReceiver.EXTRA_DESCRIPTION, reminder.description)
            putExtra(ReminderNotificationReceiver.EXTRA_ARTICLE, reminder.rebtArticle)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val triggerTime = reminder.dueDate
        if (triggerTime > System.currentTimeMillis()) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
                } else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
                }
            } catch (e: SecurityException) {
                // If exact alarm permission is restricted, fallback to standard inexact alarm
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            } catch (e: Exception) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            }
        }
    }

    fun cancelReminderAlarm(context: Context, reminderId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val intent = Intent(context, ReminderNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    fun sendTestNotification(context: Context, title: String, description: String, article: String) {
        ensureNotificationChannel(context)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val openAppIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("navigate_to", "reminders")
        }
        val pendingOpenIntent = PendingIntent.getActivity(
            context,
            99999,
            openAppIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setContentTitle("⚡ Recordatorio de Prueba: $title")
            .setContentText("$description ($article)")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("$description\n\n📌 Artículo / Norma: $article\n🔔 Notificación de prueba funcionando correctamente.")
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingOpenIntent)
            .build()

        notificationManager.notify((System.currentTimeMillis() % 10000).toInt(), notification)
    }

    fun calculateNextDueDate(currentDueDate: Long, periodicity: String): Long {
        val cal = Calendar.getInstance()
        cal.timeInMillis = if (currentDueDate > System.currentTimeMillis()) currentDueDate else System.currentTimeMillis()

        when (periodicity) {
            "Diaria" -> cal.add(Calendar.DAY_OF_YEAR, 1)
            "Semanal" -> cal.add(Calendar.WEEK_OF_YEAR, 1)
            "Mensual" -> cal.add(Calendar.MONTH, 1)
            "Anual" -> cal.add(Calendar.YEAR, 1)
            else -> return currentDueDate
        }
        return cal.timeInMillis
    }

    fun formatDateTime(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("es", "ES"))
        return sdf.format(Date(timestamp))
    }

    fun formatDateOnly(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES"))
        return sdf.format(Date(timestamp))
    }

    fun formatTimeOnly(timestamp: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale("es", "ES"))
        return sdf.format(Date(timestamp))
    }

    fun getRelativeTimeSpanString(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = timestamp - now
        val absDiff = Math.abs(diff)

        val minutes = absDiff / (60 * 1000)
        val hours = absDiff / (60 * 60 * 1000)
        val days = absDiff / (24 * 60 * 60 * 1000)

        return if (diff < 0) {
            when {
                days > 0 -> "Vencido hace $days ${if (days == 1L) "día" else "días"}"
                hours > 0 -> "Vencido hace $hours ${if (hours == 1L) "hora" else "horas"}"
                else -> "Vencido hace $minutes min"
            }
        } else {
            when {
                days > 1 -> "Vence en $days días"
                days == 1L -> "Vence mañana"
                hours > 0 -> "Vence en $hours h"
                else -> "Vence en $minutes min"
            }
        }
    }
}
