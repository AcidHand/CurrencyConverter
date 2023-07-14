package com.acidhand.currencyconverter.presentation.screen.auth.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.acidhand.currencyconverter.presentation.screen.auth.AuthAction
import com.acidhand.currencyconverter.presentation.screen.auth.AuthState
import com.acidhand.currencyconverter.utils.CONST.DIGIT_EIGHT
import com.acidhand.currencyconverter.utils.CONST.DIGIT_FIVE
import com.acidhand.currencyconverter.utils.CONST.DIGIT_FOUR
import com.acidhand.currencyconverter.utils.CONST.DIGIT_NINE
import com.acidhand.currencyconverter.utils.CONST.DIGIT_ONE
import com.acidhand.currencyconverter.utils.CONST.DIGIT_SEVEN
import com.acidhand.currencyconverter.utils.CONST.DIGIT_SIX
import com.acidhand.currencyconverter.utils.CONST.DIGIT_THREE
import com.acidhand.currencyconverter.utils.CONST.DIGIT_TWO
import com.acidhand.currencyconverter.utils.CONST.DIGIT_ZERO

@Composable
fun AuthContent(state: AuthState, action: (AuthAction) -> Unit) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {
        OtpDigits(
            code = state.code,
            modifier = Modifier
                .padding(top = 100.dp)
                .align(Alignment.TopCenter)
        )
        Keyboard(
            disabledPositions = emptySet(),
            modifier = Modifier.align(Alignment.BottomCenter),
            action = action
        )
    }
}

@Composable
private fun Keyboard(
    modifier: Modifier = Modifier,
    disabledPositions: Set<ButtonPosition>,
    action: (AuthAction) -> Unit
) {
    NumericKeyboard(
        modifier = modifier,
        disabledPositions = disabledPositions,
        onClick = { position ->
            when (position) {
                ButtonPosition.DIGIT_1 -> action(AuthAction.AddDigit(DIGIT_ONE))
                ButtonPosition.DIGIT_2 -> action(AuthAction.AddDigit(DIGIT_TWO))
                ButtonPosition.DIGIT_3 -> action(AuthAction.AddDigit(DIGIT_THREE))
                ButtonPosition.DIGIT_4 -> action(AuthAction.AddDigit(DIGIT_FOUR))
                ButtonPosition.DIGIT_5 -> action(AuthAction.AddDigit(DIGIT_FIVE))
                ButtonPosition.DIGIT_6 -> action(AuthAction.AddDigit(DIGIT_SIX))
                ButtonPosition.DIGIT_7 -> action(AuthAction.AddDigit(DIGIT_SEVEN))
                ButtonPosition.DIGIT_8 -> action(AuthAction.AddDigit(DIGIT_EIGHT))
                ButtonPosition.DIGIT_9 -> action(AuthAction.AddDigit(DIGIT_NINE))
                ButtonPosition.LEFT_ACTION -> Unit
                ButtonPosition.DIGIT_0 -> action(AuthAction.AddDigit(DIGIT_ZERO))
                ButtonPosition.RIGHT_ACTION -> action(AuthAction.DeleteDigit)
            }
        }
    )
}

@Composable
fun OtpDigits(
    modifier: Modifier = Modifier,
    code: String,
    codeLength: Int = 4
) {
    Row(
        modifier = modifier,
    ) {
        for (index in 0 until codeLength) {
            val digit = if (index <= code.lastIndex) code[index] else null
            if (index != 0) Spacer(modifier = Modifier.width(width = 8.dp))
            Box(
                modifier = Modifier
                    .widthIn(min = 54.dp)
                    .heightIn(min = 60.dp)
                    .background(
                        MaterialTheme.colors.primaryVariant,
                        shape = RoundedCornerShape(16.dp)
                    ),
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = digit?.toString().orEmpty(),
                    color = MaterialTheme.colors.onSurface,
                    style = MaterialTheme.typography.h4
                )
            }
        }
    }
}