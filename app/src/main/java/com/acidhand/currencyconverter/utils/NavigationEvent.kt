package com.acidhand.currencyconverter.utils

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

val navigationEvent =
    MutableSharedFlow<NavigationEvent>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

val errorEvent =
    MutableSharedFlow<String?>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

sealed class NavigationEvent {
    object Auth : NavigationEvent()
    object Home : NavigationEvent()
}