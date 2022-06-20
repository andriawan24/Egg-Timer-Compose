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
        val channelID = "channel-0"
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

        notificationManager.notify(0, builder.build())
    }

    fun showNotificationWithProgress(progress: Int, showTime: String) {
        val channelID = "channel-0"
        val builder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_logo)
            .setContentTitle("Timer is running")
            .setContentText(showTime)
            .setProgress(100, progress, false)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)

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

        notificationManager.notify(0, builder.build())
    }
}