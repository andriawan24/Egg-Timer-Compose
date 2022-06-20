package com.andriawan.boilanegg.ui.pages.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.andriawan.boilanegg.R
import com.andriawan.boilanegg.navigation.Routes
import com.andriawan.boilanegg.ui.theme.BoilAnEggTheme
import com.andriawan.boilanegg.ui.theme.Divider
import com.andriawan.boilanegg.utils.NotificationUtil
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
                onClick = {
                    if (selectedEgg != -1) {
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
            ) {
                Text(text = stringResource(id = R.string.button_start_title))
            }
        }
    }
}

@Composable
fun EggChooser(
    imagePainter: Painter,
    title: String,
    onClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = if (isSelected) Divider else Color.Transparent
                )
            )
            .clickable {
                onClick.invoke()
            }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Image(
            painter = imagePainter,
            contentDescription = "Soft Egg",
            modifier = Modifier.size(150.dp)
        )

        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h3
        )
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

