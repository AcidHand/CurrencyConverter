package com.acidhand.currencyconverter.presentation.screen.base.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.acidhand.currencyconverter.presentation.compose.theme.Shapes

@Composable
fun Snackbar(
    modifier: Modifier,
    backgroundColor: Color = Color.Red,
    textColor: Color = Color.White,
    message: String,
) {
    androidx.compose.material.Snackbar(
        modifier = modifier,
        shape = Shapes.small,
        backgroundColor = backgroundColor
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = message,
            color = textColor,
        )
    }
}
