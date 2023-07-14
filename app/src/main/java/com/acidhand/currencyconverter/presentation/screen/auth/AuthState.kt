package com.acidhand.currencyconverter.presentation.screen.auth

data class AuthState(
    val code: String,
) {
    companion object {
        val INITIAL = AuthState(code = "")
    }
}
