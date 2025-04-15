package de.tebbeubben.chargequicktile

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.IBinder

//This doesn't actually update the QS tile, but it keeps the app process alive
class UpdaterService : Service() {

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "foreground_channel"
        const val NOTIFICATION_ID = 1
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(
                NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    getString(R.string.foreground_notification),
                    NotificationManager.IMPORTANCE_LOW
                )
            )
        val notification = Notification.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.foreground_notification))
            .setContentText(getString(R.string.you_can_hide_this_notification))
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_battery_profile)
            .build()
        startForeground(
            NOTIFICATION_ID,
            notification,
            ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

}