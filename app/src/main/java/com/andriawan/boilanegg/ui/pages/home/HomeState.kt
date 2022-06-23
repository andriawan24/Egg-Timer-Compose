package com.andriawan.boilanegg.ui.pages.home

import com.andriawan.boilanegg.models.EggLevel

data class HomeState(
    val eggLevels: List<EggLevel>? = null,
    val selectedEgg: Int = -1
)
