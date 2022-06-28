package com.andriawan.boilanegg.utils

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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

    @SuppressLint("UnspecifiedImmutableFlag")
    fun showNotificationWithProgress(
        progress: Int,
        showTime: String,
        type: String
    ) {
        val notificationAction: NotificationCompat.Action
        if (type == StatePlayerReceiver.STATUS_PAUSE) {
            val action = Intent(context, StatePlayerReceiver::class.java).apply {
                action = StatePlayerReceiver.ACTION_NAME
                putExtra(StatePlayerReceiver.EXTRAS_STATUS, StatePlayerReceiver.STATUS_RESUME)
            }

            val intent = PendingIntent.getBroadcast(
                context,
                1,
                action,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            notificationAction = NotificationCompat.Action(
                R.drawable.ic_play,
                context.getString(R.string.button_play_title),
                intent
            )
        } else {
            val actionResume = Intent(context, StatePlayerReceiver::class.java).apply {
                action = StatePlayerReceiver.ACTION_NAME
                putExtra(StatePlayerReceiver.EXTRAS_STATUS, StatePlayerReceiver.STATUS_PAUSE)
            }

            val intent = PendingIntent.getBroadcast(
                context,
                1,
                actionResume,
                PendingIntent.FLAG_UPDATE_CURRENT
            )

            notificationAction = NotificationCompat.Action(
                R.drawable.ic_pause,
                context.getString(R.string.button_pause_title),
                intent
            )
        }

        val actionStop = Intent(context, StatePlayerReceiver::class.java).apply {
            action = StatePlayerReceiver.ACTION_NAME
            putExtra(StatePlayerReceiver.EXTRAS_STATUS, StatePlayerReceiver.STATUS_STOP)
        }

        val pendingIntentActionStop = PendingIntent.getBroadcast(
            context,
            2,
            actionStop,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationActionStop = NotificationCompat.Action(
            R.drawable.ic_stop,
            context.getString(R.string.button_stop_title),
            pendingIntentActionStop
        )

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
            .addAction(notificationAction)
            .addAction(notificationActionStop)

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
