package com.acidhand.currencyconverter.presentation.screen.auth

sealed class AuthAction {
    data class AddDigit(val value: String) : AuthAction()
    object DeleteDigit : AuthAction()
    object DismissError: AuthAction()
}
