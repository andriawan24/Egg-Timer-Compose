package com.andriawan.boilanegg.navigation

sealed class Routes(val routeName: String) {
    object Home: Routes(routeName = "home_page")
    object Timer: Routes(routeName = "timer_page") {
        const val OPTION_ID = "option_id"
    }
}
