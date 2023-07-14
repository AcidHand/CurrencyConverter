package com.acidhand.currencyconverter.presentation.screen.base

import com.acidhand.currencyconverter.utils.NavigationEvent

data class BaseState(
    val navigationEvent: NavigationEvent,
    val errorEvent: String?
) {
    companion object {
        val INITIAL = BaseState(
            navigationEvent = NavigationEvent.Auth,
            errorEvent = null
        )
    }
}
