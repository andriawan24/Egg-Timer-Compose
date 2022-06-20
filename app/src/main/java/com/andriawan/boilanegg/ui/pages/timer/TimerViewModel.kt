package com.andriawan.boilanegg.ui.pages.timer

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.andriawan.boilanegg.data.getEggLevelDummyData
import com.andriawan.boilanegg.utils.NotificationUtil
import timber.log.Timber

class TimerViewModel : ViewModel() {
    var state by mutableStateOf(TimerState())
        private set

    private var countDownTimer: CountDownTimer? = null
    private var notificationUtil: NotificationUtil? = null

    fun getEggLevel(id: Int, notificationUtil: NotificationUtil) {
        val eggList = getEggLevelDummyData()
        val eggLevel = eggList.firstOrNull { it.id == id }
        state = state.copy(
            eggLevel = eggLevel,
            secondsRemaining = eggLevel?.duration?.times(60) ?: 0,
            showTime = formatTimer(eggLevel?.duration?.times(60) ?: 0),
            percentageProgress = 1F
        )
        this.notificationUtil = notificationUtil
        initTimer()
    }

    fun onEvent(event: TimerStateUIEvent) {
        when (event) {
            TimerStateUIEvent.InitTimer -> {
                initTimer()
            }

            TimerStateUIEvent.StartTimer -> {
                countDownTimer?.start()
                state = state.copy(
                    isStarted = true,
                    isPlaying = true
                )
            }

            TimerStateUIEvent.PauseTimer -> {
                countDownTimer?.cancel()
                initTimer()
                state = state.copy(
                    isPlaying = false
                )
                notificationUtil?.showNotification(
                    title = "Paused",
                    body = "Timer already paused"
                )
            }

            TimerStateUIEvent.StopTimer -> {
                countDownTimer?.cancel()
                state = state.copy(
                    secondsRemaining = state.eggLevel?.duration?.times(60) ?: 0,
                    isStarted = false,
                    isPlaying = false,
                    isFinished = false,
                    showTime = formatTimer(state.eggLevel?.duration?.times(60) ?: 0),
                    percentageProgress = 1F
                )
                initTimer()
                notificationUtil?.showNotification(
                    title = "Stopped",
                    body = "Timer already stopped"
                )
            }
        }
    }

    private fun initTimer() {
        val totalTime = state.secondsRemaining.times(1000)
        val totalMinuteTime = state.eggLevel?.duration?.times(60) ?: 0
        countDownTimer = object : CountDownTimer(totalTime.toLong(), 1000L) {
            override fun onTick(p0: Long) {
                Timber.i("Seconds Remaining ${p0 / 1000}")
                state = state.copy(
                    showTime = formatTimer((p0 / 1000).toInt()),
                    secondsRemaining = (p0 / 1000).toInt(),
                    percentageProgress = (p0.toFloat() / 1000) / totalMinuteTime.toFloat()
                )
                notificationUtil?.showNotificationWithProgress(
                    progress = ((p0.toFloat() / 1000) / totalMinuteTime.toFloat() * 100).toInt(),
                    showTime = formatTimer((p0 / 1000).toInt())
                )
            }

            override fun onFinish() {
                state = state.copy(
                    isFinished = true,
                    showTime = "Finished!",
                    isPlaying = false,
                    isStarted = false,
                    percentageProgress = 0F,
                    secondsRemaining = state.eggLevel?.duration?.times(60) ?: 0,
                )
                notificationUtil?.showNotification(
                    title = "Finished",
                    body = "Timer already ended"
                )
            }
        }
    }

    private fun formatTimer(secondsRemaining: Int): String {
        val minutes = secondsRemaining / 60
        var minuteString = "00"
        var secondsString = "00"
        if (minutes > 0) {
            minuteString = if (minutes >= 10) {
                minutes.toString()
            } else {
                "0$minutes"
            }
        }

        val seconds = secondsRemaining % 60

        if (seconds > 0) {
            secondsString = if (seconds >= 10) {
                seconds.toString()
            } else {
                "0$seconds"
            }
        }

        return "${minuteString}:${secondsString}"
    }

    override fun onCleared() {
        super.onCleared()
        countDownTimer?.cancel()
    }
}