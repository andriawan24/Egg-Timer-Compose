package com.andriawan.boilanegg.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andriawan.boilanegg.R
import com.andriawan.boilanegg.ui.theme.Divider

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
fun EggChooserPreview() {
    Surface(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(12.dp)
    ) {
        EggChooser(
            imagePainter = painterResource(id = R.drawable.ic_soft) ,
            title = "Soft",
            onClick = { },
            isSelected = false
        )
    }
}

@Preview
@Composable
fun EggChooserSelectedPreview() {
    Surface(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(12.dp)
    ) {
        EggChooser(
            imagePainter = painterResource(id = R.drawable.ic_soft) ,
            title = "Soft",
            onClick = { },
            isSelected = true
        )
    }
}