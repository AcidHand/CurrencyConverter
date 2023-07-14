package com.acidhand.currencyconverter.presentation.screen.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acidhand.currencyconverter.utils.errorEvent
import com.acidhand.currencyconverter.utils.navigationEvent
import com.acidhand.currencyconverter.utils.provideDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor() : ViewModel(){
    private val _baseState = MutableStateFlow(BaseState.INITIAL)
    private var baseState: BaseState by _baseState
    val uiState = _baseState.asStateFlow()

    init {
        navigationEvent
            .onEach { baseState = baseState.copy(navigationEvent = it) }
            .launchIn(viewModelScope)

        errorEvent
            .onEach { baseState = baseState.copy(errorEvent = it) }
            .launchIn(viewModelScope)
    }
}