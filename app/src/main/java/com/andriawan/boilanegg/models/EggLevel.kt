package com.andriawan.boilanegg.models

import androidx.annotation.DrawableRes
import com.andriawan.boilanegg.R

data class EggLevel(
    val name: String,
    @DrawableRes val icon: Int,
    val id: Int,
    val duration: Int = 0
)

fun getEggLevelDummyData(): List<EggLevel> {
    return listOf(
        EggLevel(
            name = "Soft",
            icon = R.drawable.ic_soft,
            id = 1,
            duration = 5
        ),
        EggLevel(
            name = "Medium",
            icon = R.drawable.ic_medium,
            id = 2,
            duration = 7
        ),
        EggLevel(
            name = "Hard",
            icon = R.drawable.ic_hard,
            id = 3,
            duration = 12
        )
    )
}