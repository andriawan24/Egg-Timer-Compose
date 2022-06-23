package com.andriawan.boilanegg.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.andriawan.boilanegg.R

class NotificationUtil(
    private val context: Context
) {

    fun showNotification(title: String, body: String) {
        val channelID = DEFAULT_CHANNEL_ID
        val builder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_MAX)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Egg Timer Channel"
            val descriptionText = "This notification is for creating progress bar for timer"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(DEFAULT_NOTIFICATION_ID, builder.build())
    }

    fun showNotificationWithProgress(progress: Int, showTime: String) {
        val channelID = DEFAULT_CHANNEL_ID
        val builder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle("Timer is running")
            .setContentText(showTime)
            .setProgress(MAX_PROGRESS, progress, false)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setShowWhen(progress == 100)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Egg Timer Channel"
            val descriptionText = "This notification is for creating progress bar for timer"
            val importance = NotificationManager.IMPORTANCE_LOW
            val channel = NotificationChannel(channelID, name, importance).apply {
                description = descriptionText
                setShowBadge(false)
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(DEFAULT_NOTIFICATION_ID, builder.build())
    }

    companion object {
        private const val MAX_PROGRESS = 100
        private const val DEFAULT_NOTIFICATION_ID = 0
        private const val DEFAULT_CHANNEL_ID = "channel-0"
    }
}
