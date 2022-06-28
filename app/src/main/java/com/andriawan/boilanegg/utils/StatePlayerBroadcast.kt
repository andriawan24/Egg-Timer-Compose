package com.andriawan.boilanegg.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

open class StatePlayerBroadcast: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        // do nothing
    }

    companion object {
        const val ACTION_NAME = "ReceiveStatus"
    }
}