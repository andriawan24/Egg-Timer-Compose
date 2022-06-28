package com.andriawan.boilanegg.ui.pages.timer

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.andriawan.boilanegg.models.getEggLevelDummyData
import com.andriawan.boilanegg.utils.NotificationUtil
import com.andriawan.boilanegg.utils.StatePlayerReceiver
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
            secondsRemaining = eggLevel?.duration?.times(MINUTE_IN_SECONDS) ?: DEFAULT_DURATION,
            showTime = formatTimer(
                eggLevel?.duration?.times(MINUTE_IN_SECONDS) ?: DEFAULT_DURATION
            ),
            percentageProgress = MAX_VALUE_PROGRESS_BAR
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
                val totalMinuteTime =
                    state.eggLevel?.duration?.times(MINUTE_IN_SECONDS) ?: DEFAULT_DURATION
                var percentage = (state.secondsRemaining.toFloat()) / totalMinuteTime.toFloat()
                percentage *= ONE_HUNDRED
                notificationUtil?.showNotificationWithProgress(
                    progress = percentage.toInt(),
                    showTime = formatTimer(state.secondsRemaining),
                    type = StatePlayerReceiver.STATUS_PAUSE
                )
            }

            TimerStateUIEvent.StopTimer -> {
                countDownTimer?.cancel()
                state = state.copy(
                    secondsRemaining = state.eggLevel?.duration?.times(MINUTE_IN_SECONDS)
                        ?: DEFAULT_DURATION,
                    isStarted = false,
                    isPlaying = false,
                    isFinished = false,
                    showTime = formatTimer(
                        state.eggLevel?.duration?.times(MINUTE_IN_SECONDS) ?: DEFAULT_DURATION
                    ),
                    percentageProgress = MAX_VALUE_PROGRESS_BAR
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
        val totalTime = state.secondsRemaining.times(SECOND_IN_MILLIS)
        val totalMinuteTime = state.eggLevel?.duration?.times(MINUTE_IN_SECONDS) ?: DEFAULT_DURATION
        countDownTimer = object : CountDownTimer(totalTime.toLong(), SECOND_IN_MILLIS.toLong()) {
            override fun onTick(secondsRemaining: Long) {
                Timber.i("Seconds Remaining ${secondsRemaining / SECOND_IN_MILLIS}")
                state = state.copy(
                    showTime = formatTimer((secondsRemaining / SECOND_IN_MILLIS).toInt()),
                    secondsRemaining = (secondsRemaining / SECOND_IN_MILLIS).toInt(),
                    percentageProgress = (secondsRemaining.toFloat() / SECOND_IN_MILLIS) / totalMinuteTime.toFloat()
                )
                var percentage =
                    (secondsRemaining.toFloat() / SECOND_IN_MILLIS) / totalMinuteTime.toFloat()
                percentage *= ONE_HUNDRED
                notificationUtil?.showNotificationWithProgress(
                    progress = percentage.toInt(),
                    showTime = formatTimer((secondsRemaining / SECOND_IN_MILLIS).toInt()),
                    type = StatePlayerReceiver.STATUS_RESUME
                )
            }

            override fun onFinish() {
                state = state.copy(
                    isFinished = true,
                    showTime = "Finished!",
                    isPlaying = false,
                    isStarted = false,
                    percentageProgress = MIN_VALUE_PROGRESS_BAR,
                    secondsRemaining = state.eggLevel?.duration?.times(MINUTE_IN_SECONDS)
                        ?: DEFAULT_DURATION,
                )
                notificationUtil?.showNotification(
                    title = "Finished",
                    body = "Timer already ended"
                )
            }
        }
    }

    private fun formatTimer(secondsRemaining: Int): String {
        val minutes = secondsRemaining / MINUTE_IN_SECONDS
        var minuteString = "00"
        var secondsString = "00"
        if (minutes > DEFAULT_DURATION) {
            minuteString = if (minutes >= TEN) {
                minutes.toString()
            } else {
                "0$minutes"
            }
        }

        val seconds = secondsRemaining % MINUTE_IN_SECONDS

        if (seconds > DEFAULT_DURATION) {
            secondsString = if (seconds >= TEN) {
                seconds.toString()
            } else {
                "0$seconds"
            }
        }

        return "$minuteString:$secondsString"
    }

    override fun onCleared() {
        super.onCleared()
        countDownTimer?.cancel()
    }

    companion object {
        private const val MINUTE_IN_SECONDS = 60
        private const val SECOND_IN_MILLIS = 1000
        private const val DEFAULT_DURATION = 0

        private const val ONE_HUNDRED = 100
        private const val TEN = 10

        private const val MAX_VALUE_PROGRESS_BAR = 1F
        private const val MIN_VALUE_PROGRESS_BAR = 0F
    }
}
