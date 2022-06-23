package com.andriawan.boilanegg.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andriawan.boilanegg.R
import com.andriawan.boilanegg.ui.theme.BoilAnEggTheme

@Composable
fun PlaybackButton(
    isStarted: Boolean,
    isFinished: Boolean,
    isPlaying: Boolean,
    onPauseTimer: () -> Unit,
    onStartTimer: () -> Unit,
    onStopTimer: () -> Unit
) {
    Row {
        if (isStarted && !isFinished) {
            Button(
                modifier = Modifier.weight(1F),
                onClick = {
                    if (isPlaying) {
                        onPauseTimer.invoke()
                    } else {
                        onStartTimer.invoke()
                    }
                },
            ) {
                Text(
                    text = if (isPlaying) {
                        stringResource(id = R.string.button_pause_title)
                    } else {
                        stringResource(id = R.string.button_resume_title)
                    },
                    style = MaterialTheme.typography.button
                )
            }

            Spacer(modifier = Modifier.size(20.dp))
        }

        Button(
            modifier = Modifier.weight(1F),
            onClick = {
                if (isStarted)
                    onStopTimer.invoke()
                else
                    onStartTimer.invoke()
            }
        ) {
            Text(
                text = if (!isStarted) {
                    stringResource(id = R.string.button_play_title)
                } else {
                    stringResource(id = R.string.button_stop_title)
                }
            )
        }
    }
}

@Preview
@Composable
fun PlaybackButtonPreview() {
    BoilAnEggTheme {
        Surface(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(24.dp)
        ) {
            PlaybackButton(
                isStarted = true,
                isFinished = false,
                isPlaying = true,
                onPauseTimer = { },
                onStartTimer = { },
                onStopTimer = { }
            )
        }
    }
}
