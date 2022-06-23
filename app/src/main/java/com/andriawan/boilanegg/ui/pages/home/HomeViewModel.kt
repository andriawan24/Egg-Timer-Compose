package com.andriawan.boilanegg.ui.pages.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.andriawan.boilanegg.models.getEggLevelDummyData

class HomeViewModel : ViewModel() {

    var state by mutableStateOf(
        HomeState(
            eggLevels = getEggLevelDummyData()
        )
    )
        private set

    fun selectEgg(id: Int) {
        state = state.copy(
            selectedEgg = id
        )
    }
}
