package com.acidhand.currencyconverter.data

import com.acidhand.currencyconverter.domain.usecase.IAccessUseCase
import com.acidhand.currencyconverter.utils.CONST.ATTEMPTS_LEFT_DEFAULT
import com.acidhand.currencyconverter.utils.CONST.BLOCK_TIMEOUT
import com.acidhand.currencyconverter.utils.DataStoreUtils
import javax.inject.Inject

class AccessUseCaseImpl @Inject constructor(private val dataStore: DataStoreUtils) :
    IAccessUseCase {

    override fun getCodeAttemptsLeft(): Int {
        val attempts = dataStore.attemptsLeft
        return if (attempts == 1) {
            blockUser()
            0
        } else {
            val newAttempts = dataStore.attemptsLeft - 1
            dataStore.attemptsLeft = newAttempts
            newAttempts
        }
    }

    override fun clearCodeAttemptsLeft() {
        dataStore.attemptsLeft = ATTEMPTS_LEFT_DEFAULT
    }

    override fun blockUser() {
        dataStore.isUserBlocked = true
        dataStore.userBlockTime = System.currentTimeMillis()
    }

    override fun unblockUser() {
        dataStore.isUserBlocked = false
        dataStore.userBlockTime = 0
    }

    override fun isUserBlocked(): Boolean {
        val isUserBlocked = dataStore.isUserBlocked
        val userBlockTime = dataStore.userBlockTime
        val currentTime = System.currentTimeMillis()
        val isBlockTimeOver = currentTime - userBlockTime > BLOCK_TIMEOUT

        return if (isUserBlocked) {
            if (isBlockTimeOver) {
                unblockUser()
                false
            } else true
        } else false
    }
}