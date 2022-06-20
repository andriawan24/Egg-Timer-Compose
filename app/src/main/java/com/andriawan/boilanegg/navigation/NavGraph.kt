package com.andriawan.boilanegg.navigation

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.andriawan.boilanegg.ui.pages.home.HomePage
import com.andriawan.boilanegg.ui.pages.timer.TimerPage
import kotlinx.coroutines.CoroutineScope

@Composable
fun NavGraph(navController: NavHostController, scope: CoroutineScope, state: SnackbarHostState) {
    NavHost(
        navController = navController,
        startDestination = Routes.Home.routeName
    ) {

        composable(route = Routes.Home.routeName) {
            HomePage(navController = navController, snackBarState = state, scope = scope)
        }

        composable(
            route = "${Routes.Timer.routeName}/{${Routes.Timer.OPTION_ID}}",
            arguments = listOf(navArgument(Routes.Timer.OPTION_ID) { type = NavType.IntType })
        ) {
            val id = it.arguments?.getInt(Routes.Timer.OPTION_ID)
            TimerPage(
                navController = navController,
                scope = scope,
                snackBarState = state,
                eggLevelID = id ?: -1
            )
        }
    }
}