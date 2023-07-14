package com.acidhand.currencyconverter.presentation.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.acidhand.currencyconverter.presentation.compose.theme.CurrencyConverterTheme
import com.acidhand.currencyconverter.presentation.screen.auth.AuthAction
import com.acidhand.currencyconverter.presentation.screen.auth.AuthState
import com.acidhand.currencyconverter.presentation.screen.auth.AuthViewModel
import com.acidhand.currencyconverter.presentation.screen.auth.content.AuthContent
import com.acidhand.currencyconverter.presentation.screen.base.BaseViewModel
import com.acidhand.currencyconverter.presentation.screen.base.views.Snackbar
import com.acidhand.currencyconverter.presentation.screen.home.HomeAction
import com.acidhand.currencyconverter.presentation.screen.home.HomeState
import com.acidhand.currencyconverter.presentation.screen.home.content.HomeContent
import com.acidhand.currencyconverter.presentation.screen.home.content.TopBar
import com.acidhand.currencyconverter.presentation.screen.home.HomeViewModel
import com.acidhand.currencyconverter.utils.NavigationEvent
import com.acidhand.currencyconverter.utils.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val baseViewModel by viewModels<BaseViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val baseState by baseViewModel.uiState.collectAsStateWithLifecycle()
            val homeState by homeViewModel.uiState.collectAsStateWithLifecycle()
            val authState by authViewModel.uiState.collectAsStateWithLifecycle()

            val snackbarHostState = remember { SnackbarHostState() }

            CurrencyConverterTheme {
                Surface {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        when (baseState.navigationEvent) {
                            NavigationEvent.Auth -> AuthScreen(
                                state = authState,
                                action = authViewModel::actions
                            )

                            NavigationEvent.Home -> HomeScreen(
                                state = homeState,
                                actions = homeViewModel::actions
                            )
                        }

                        LaunchedEffect(key1 = baseState.errorEvent) {
                            if (baseState.errorEvent != null) {
                                snackbarHostState.showSnackbar(message = baseState.errorEvent.orEmpty())
                            } else {
                                snackbarHostState.currentSnackbarData?.dismiss()
                            }
                        }

                        SnackbarHost(hostState = snackbarHostState) {
                            Snackbar(modifier = Modifier.padding(16.dp), message = it.message)
                            authViewModel.actions(AuthAction.DismissError)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeScreen(state: HomeState, actions: (HomeAction) -> Unit) {
    Column {
        TopBar(state = state, actions = actions)
        HomeContent(state = state, actions = actions)
    }
}

@Composable
private fun AuthScreen(state: AuthState, action: (AuthAction) -> Unit) {
    AuthContent(action = action, state = state)
}

