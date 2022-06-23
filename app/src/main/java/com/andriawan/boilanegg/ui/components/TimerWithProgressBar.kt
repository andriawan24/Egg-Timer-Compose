package com.andriawan.boilanegg.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andriawan.boilanegg.ui.theme.BoilAnEggTheme

@Composable
fun TimerWithProgress(
    showTimer: String,
    progress: Float = 0F
) {
    Column {
        Text(
            text = showTimer,
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(24.dp))

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun TimerWithProgressPreview() {
    BoilAnEggTheme {
        Surface(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(24.dp)
        ) {
            TimerWithProgress(
                showTimer = "00:00"
            )
        }
    }
}
