package com.example.fitpulse.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class ReminderWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        val nm = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.createNotificationChannel(NotificationChannel("fitpulse", "FitPulse", NotificationManager.IMPORTANCE_DEFAULT))
        }
        val notif = NotificationCompat.Builder(applicationContext, "fitpulse")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("FitPulse")
            .setContentText("C'est l'heure de ta séance !")
            .setAutoCancel(true)
            .build()
        nm.notify(1, notif)
        return Result.success()
    }
}
