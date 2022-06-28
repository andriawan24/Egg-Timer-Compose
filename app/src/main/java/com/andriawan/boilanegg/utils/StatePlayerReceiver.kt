package com.andriawan.boilanegg.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class StatePlayerReceiver : BroadcastReceiver() {

    override fun onReceive(content: Context?, intent: Intent?) {
        Timber.d("On Receive ${intent?.extras}")
        val status = intent?.getStringExtra(EXTRAS_STATUS)
        val intentBroadcast = Intent(StatePlayerBroadcast.ACTION_NAME).apply {
            action = StatePlayerBroadcast.ACTION_NAME
            putExtra(EXTRAS_STATUS, status)
        }
        content?.sendBroadcast(intentBroadcast)
    }

    companion object {
        const val EXTRAS_STATUS = "status"
        const val STATUS_PAUSE = "pause"
        const val STATUS_RESUME = "resume"
        const val STATUS_STOP = "stop"
        const val ACTION_NAME = "ChangeStatus"
    }
}