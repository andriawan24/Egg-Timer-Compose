package com.andriawan.boilanegg.ui.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.andriawan.boilanegg.R
import com.andriawan.boilanegg.navigation.Routes
import com.andriawan.boilanegg.ui.components.EggChooser
import com.andriawan.boilanegg.ui.theme.BoilAnEggTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomePage(
    navController: NavController,
    scope: CoroutineScope,
    snackBarState: SnackbarHostState,
    viewModel: HomeViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    val state = viewModel.state
    val selectedEgg = state.selectedEgg
    val eggLevels = state.eggLevels
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header()

        eggLevels?.let { list ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                list.forEach {
                    EggChooser(
                        imagePainter = painterResource(id = it.icon),
                        title = it.name,
                        isSelected = selectedEgg == it.id,
                        onClick = {
                            viewModel.selectEgg(it.id)
                        },
                        modifier = Modifier.weight(1F)
                    )
                }
            }
        }

        ButtonBottomLayout(
            selectedEgg = selectedEgg,
            onEggSelected = { isSuccess ->
                if (isSuccess) {
                    navController.navigate("${Routes.Timer.routeName}/$selectedEgg")
                } else {
                    scope.launch {
                        snackBarState.currentSnackbarData?.dismiss()
                        snackBarState.showSnackbar(
                            message = context.getString(R.string.egg_chooser_required),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun Header() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp)
    ) {
        Text(
            text = stringResource(id = R.string.header_question),
            style = MaterialTheme.typography.h1
        )
    }
}

@Composable
fun ButtonBottomLayout(
    selectedEgg: Int,
    onEggSelected: (isSuccess: Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier
                .size(width = 200.dp, height = 40.dp)
                .clip(MaterialTheme.shapes.large),
            onClick = { onEggSelected(selectedEgg != -1) }
        ) {
            Text(
                text = stringResource(id = R.string.button_start_title)
            )
        }
    }
}

@Preview
@Composable
fun HomePagePreview() {
    BoilAnEggTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            HomePage(
                navController = rememberNavController(),
                scope = rememberCoroutineScope(),
                snackBarState = SnackbarHostState()
            )
        }
    }
}
