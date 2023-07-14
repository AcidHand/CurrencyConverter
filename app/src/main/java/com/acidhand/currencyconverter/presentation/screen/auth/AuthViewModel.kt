package com.acidhand.currencyconverter.presentation.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acidhand.currencyconverter.R
import com.acidhand.currencyconverter.domain.usecase.IAccessUseCase
import com.acidhand.currencyconverter.utils.CONST.AUTH_CODE
import com.acidhand.currencyconverter.utils.CONST.AUTH_CODE_LENGTH
import com.acidhand.currencyconverter.utils.NavigationEvent
import com.acidhand.currencyconverter.utils.errorEvent
import com.acidhand.currencyconverter.utils.navigationEvent
import com.acidhand.currencyconverter.utils.provideDelegate
import com.acidhand.currencyconverter.utils.resourse_provider.IResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val access: IAccessUseCase,
    private val resourcesProvider: IResourceProvider
    ) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState.INITIAL)
    private var authState: AuthState by _authState
    val uiState = _authState.asStateFlow()

    fun actions(action: AuthAction) {
        when (action) {
            is AuthAction.AddDigit -> {
                if (authState.code.length < AUTH_CODE_LENGTH) {
                    authState = authState.copy(code = authState.code + action.value)

                    if (authState.code == AUTH_CODE && authState.code.length == AUTH_CODE_LENGTH) {
                        if (access.isUserBlocked()) {
                            val error = resourcesProvider.getString(R.string.blocked_try_later)
                            errorEvent.tryEmit(error)
                        } else {
                            navigationEvent.tryEmit(NavigationEvent.Home)
                            access.clearCodeAttemptsLeft()
                        }
                    }
                    if (authState.code != AUTH_CODE && authState.code.length == AUTH_CODE_LENGTH) {
                        if (access.isUserBlocked()) {
                            val error = resourcesProvider.getString(R.string.blocked_try_later)
                            errorEvent.tryEmit(error)
                        } else {
                            val attempt = access.getCodeAttemptsLeft()
                            if (attempt == 0) {
                                val error = resourcesProvider.getString(R.string.blocked_limit_exceeded)
                                errorEvent.tryEmit(error)
                            } else {
                                val error =resourcesProvider.getString(R.string.wrong_code_attempts_dec, attempt)
                                errorEvent.tryEmit(error)
                            }
                        }
                    }
                }
            }

            AuthAction.DeleteDigit -> authState = authState.copy(code = authState.code.dropLast(1))
            AuthAction.DismissError -> {
                viewModelScope.launch {
                    delay(3000)
                    authState = authState.copy(code = "")
                    errorEvent.tryEmit(null)
                }
            }
        }
    }
}