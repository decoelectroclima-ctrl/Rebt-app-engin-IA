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

object DailyStudyNotificationManager {
    const val CHANNEL_ID = "rebt_daily_study_channel"
    private const val CHANNEL_NAME = "Recordatorio Diario de Estudio"
    private const val CHANNEL_DESCRIPTION = "Avisos diarios si no has entrado a repasar ninguna ITC o realizar un test del REBT"
    const val DAILY_STUDY_ALARM_REQ_CODE = 88001
    const val DAILY_STUDY_NOTIFICATION_ID = 88002

    fun ensureDailyStudyChannel(context: Context) {
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

    fun scheduleDailyStudyAlarm(
        context: Context,
        hour: Int = 20,
        minute: Int = 0,
        enabled: Boolean = true
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val intent = Intent(context, DailyStudyReminderReceiver::class.java).apply {
            action = DailyStudyReminderReceiver.ACTION_TRIGGER_DAILY_STUDY_REMINDER
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            DAILY_STUDY_ALARM_REQ_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (!enabled) {
            alarmManager.cancel(pendingIntent)
            return
        }

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        val triggerTime = calendar.timeInMillis
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            }
        } catch (e: SecurityException) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
        } catch (e: Exception) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
        }
    }

    fun cancelDailyStudyAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val intent = Intent(context, DailyStudyReminderReceiver::class.java).apply {
            action = DailyStudyReminderReceiver.ACTION_TRIGGER_DAILY_STUDY_REMINDER
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            DAILY_STUDY_ALARM_REQ_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    suspend fun checkAndSendDailyStudyNotification(context: Context) {
        ensureDailyStudyChannel(context)
        val db = EnigmaDatabase.getDatabase(context)
        val activity = db.enigmaDao().getDailyActivityDirect()

        val todayDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val hasStudiedToday = activity != null &&
                activity.lastActiveDate == todayDate &&
                (activity.questionsAnswered > 0 || activity.studiedItcsToday.isNotBlank())

        // If the user already studied today, do not disturb!
        if (hasStudiedToday) {
            return
        }

        // The user hasn't studied today -> trigger motivating reminder
        val streak = activity?.streakDays ?: 0
        val dayIndex = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val suggestedItc = Content.SYLLABUS.getOrNull(dayIndex % Content.SYLLABUS.size)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Intent to open app into Study tab
        val openStudyIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("navigate_to", "study")
        }
        val pendingStudyIntent = PendingIntent.getActivity(
            context,
            88003,
            openStudyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Intent to open app into Exams tab
        val openExamsIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("navigate_to", "exams")
        }
        val pendingExamsIntent = PendingIntent.getActivity(
            context,
            88004,
            openExamsIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val title = if (streak > 0) {
            "⚡ ¡Mantén tu racha de $streak días de REBT!"
        } else {
            "⚡ ¡Tu meta diaria de REBT te espera!"
        }

        val suggestedCode = suggestedItc?.code ?: "ITC-BT-05"
        val suggestedTitle = suggestedItc?.title ?: "Verificaciones e Inspecciones"
        val shortText = "Aún no has estudiado ninguna ITC hoy. Dedica 3 minutos a repasar $suggestedCode."
        val expandedText = "Hoy no has registrado actividad de estudio en el Reglamento Electrotécnico.\n\n" +
                "📌 ITC Recomendada de hoy: $suggestedCode ($suggestedTitle)\n" +
                "💡 Repasar un artículo al día consolida tus conocimientos y garantiza tu apto en el examen oficial."

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setContentTitle(title)
            .setContentText(shortText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(expandedText))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingStudyIntent)
            .addAction(android.R.drawable.ic_menu_agenda, "📖 Repasar ITC", pendingStudyIntent)
            .addAction(android.R.drawable.ic_media_play, "⚡ Hacer Test", pendingExamsIntent)
            .build()

        notificationManager.notify(DAILY_STUDY_NOTIFICATION_ID, notification)
    }

    fun sendTestDailyStudyNotification(context: Context) {
        ensureDailyStudyChannel(context)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val openStudyIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("navigate_to", "study")
        }
        val pendingStudyIntent = PendingIntent.getActivity(
            context,
            88005,
            openStudyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val openExamsIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("navigate_to", "exams")
        }
        val pendingExamsIntent = PendingIntent.getActivity(
            context,
            88006,
            openExamsIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setContentTitle("⚡ Prueba de Recordatorio Diario REBT")
            .setContentText("Aún no has estudiado ninguna ITC hoy. Dedica 3 minutos al temario.")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(
                        "Notificación de prueba del Recordatorio Diario de Estudio.\n\n" +
                        "📌 Funcionamiento: Si llega la hora configurada (ej. 20:00) y no has estudiado ninguna ITC ni hecho ningún examen hoy, recibirás este aviso automáticamente."
                    )
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentIntent(pendingStudyIntent)
            .addAction(android.R.drawable.ic_menu_agenda, "📖 Repasar ITC", pendingStudyIntent)
            .addAction(android.R.drawable.ic_media_play, "⚡ Hacer Test", pendingExamsIntent)
            .build()

        notificationManager.notify(DAILY_STUDY_NOTIFICATION_ID, notification)
    }
}
