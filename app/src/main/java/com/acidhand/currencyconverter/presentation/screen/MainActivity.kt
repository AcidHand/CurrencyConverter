package com.acidhand.currencyconverter.presentation.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.acidhand.currencyconverter.presentation.compose.theme.CurrencyConverterTheme
import com.acidhand.currencyconverter.presentation.screen.composable.MainContent
import com.acidhand.currencyconverter.presentation.screen.composable.TopBar
import com.acidhand.currencyconverter.presentation.screen.state.ActionMain
import com.acidhand.currencyconverter.presentation.screen.state.MainState
import com.acidhand.currencyconverter.utils.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state by viewModel.uiState.collectAsStateWithLifecycle()

            CurrencyConverterTheme {
                Surface {
                    MainScreen(uiState = state, actions = viewModel::actions)
                }
            }
        }
    }
}

@Composable
private fun MainScreen(
    uiState: MainState,
    actions: (ActionMain) -> Unit
) {
    Column {
        TopBar(uiState = uiState, actions = actions)

        MainContent(uiState = uiState, actions = actions)
    }
}
