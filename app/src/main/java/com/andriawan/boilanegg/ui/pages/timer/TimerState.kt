package com.andriawan.boilanegg.ui.pages.timer

import com.andriawan.boilanegg.data.EggLevel

data class TimerState(
    val eggLevel: EggLevel? = null,
    val isPlaying: Boolean = false,
    val isStarted: Boolean = false,
    val isFinished: Boolean = false,
    val showTime: String = "00:00",
    val secondsRemaining: Int = 0,
    val percentageProgress: Float = 0F
)