package com.acidhand.currencyconverter.domain.usecase

interface IAccessUseCase {
    fun getCodeAttemptsLeft(): Int
    fun clearCodeAttemptsLeft()
    fun blockUser()
    fun unblockUser()
    fun isUserBlocked(): Boolean
}