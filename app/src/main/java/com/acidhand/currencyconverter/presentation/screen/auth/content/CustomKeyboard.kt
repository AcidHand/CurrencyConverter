package com.acidhand.currencyconverter.presentation.screen.auth.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NumericKeyboard(
    modifier: Modifier = Modifier,
    // digitButtonTextStyle: TextStyle = TextStyle.FULL_STANDALONE,
    buttonHeight: Dp = 60.dp,
    onClick: (ButtonPosition) -> Unit,
    disabledPositions: Set<ButtonPosition> = emptySet(),
    leftAction: @Composable () -> Unit = {},
    rightAction: @Composable () -> Unit = {
        val enabled = !disabledPositions.contains(ButtonPosition.RIGHT_ACTION)
        Icon(
            imageVector = Icons.Filled.Delete,
            tint = if (enabled) MaterialTheme.colors.onSurface else MaterialTheme.colors.onSurface,
            contentDescription = "delete icon",
        )
    },
) {
    Column(modifier = modifier) {
        ButtonPosition.values().toList().chunked(size = 3).forEach { positions ->
            Row {
                positions.forEach { position ->
                    val enabled = !disabledPositions.contains(position)
                    TextButton(
                        modifier = Modifier
                            .weight(weight = 1f, fill = true)
                            .height(buttonHeight),
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colors.onSurface,
                            disabledContentColor = MaterialTheme.colors.onSurface,
                        ),
                        onClick = { onClick(position) },
                        enabled = enabled,
                    ) {
                        when (position) {
                            ButtonPosition.LEFT_ACTION -> leftAction()
                            ButtonPosition.RIGHT_ACTION -> rightAction()
                            else -> Text(
                                text = position.name.last().toString(),
                                color =
                                if (enabled) MaterialTheme.colors.onSurface
                                else MaterialTheme.colors.onSurface,
                                // style = digitButtonTextStyle
                            )
                        }
                    }
                }
            }
        }

    }
}

enum class ButtonPosition {
    DIGIT_1,
    DIGIT_2,
    DIGIT_3,
    DIGIT_4,
    DIGIT_5,
    DIGIT_6,
    DIGIT_7,
    DIGIT_8,
    DIGIT_9,
    LEFT_ACTION,
    DIGIT_0,
    RIGHT_ACTION,
}