package com.andriawan.boilanegg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.andriawan.boilanegg.navigation.NavGraph
import com.andriawan.boilanegg.ui.theme.BoilAnEggTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoilAnEggTheme {
                val scaffoldState = rememberScaffoldState()
                val snackBarHostState = remember {
                    SnackbarHostState()
                }
                val scope = rememberCoroutineScope()

                Scaffold(
                    scaffoldState = scaffoldState,
                    snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
                ) {
                    val navController = rememberNavController()
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        color = MaterialTheme.colors.background
                    ) {
                        NavGraph(
                            navController = navController,
                            scope = scope,
                            state = snackBarHostState
                        )
                    }
                }
            }
        }
    }
}
