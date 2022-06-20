package com.andriawan.boilanegg.ui.pages.timer

sealed class TimerStateUIEvent {
    object InitTimer : TimerStateUIEvent()
    object StartTimer : TimerStateUIEvent()
    object PauseTimer : TimerStateUIEvent()
    object StopTimer : TimerStateUIEvent()
}