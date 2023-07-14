package com.acidhand.currencyconverter.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.acidhand.currencyconverter.utils.CONST.ATTEMPTS_LEFT
import com.acidhand.currencyconverter.utils.CONST.ATTEMPTS_LEFT_DEFAULT
import com.acidhand.currencyconverter.utils.CONST.BLOCK_TIME
import com.acidhand.currencyconverter.utils.CONST.IS_BLOCKED
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreUtils @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val scope = CoroutineScope(Dispatchers.IO)

    var attemptsLeft: Int
        get() = getCodeAttemptsLeft()
        set(value) = saveCodeAttemptsLeft(value)

    var isUserBlocked: Boolean
        get() = getBoolean(IS_BLOCKED)
        set(value) = writeBoolean(IS_BLOCKED, value)

    var userBlockTime: Long
        get() = getUserBlockedTime()
        set(value) = getUserBlockedTime(value)

    private fun getCodeAttemptsLeft(): Int {
        val attempts = getString(ATTEMPTS_LEFT)
        return if (attempts.isEmpty()) ATTEMPTS_LEFT_DEFAULT else attempts.toInt()
    }

    private fun saveCodeAttemptsLeft(id: Int) {
        writeString(ATTEMPTS_LEFT, id.toString())
    }

    private fun getUserBlockedTime(): Long = getLong(BLOCK_TIME)

    private fun getUserBlockedTime(value: Long) {
        writeLong(BLOCK_TIME, value)
    }

    private fun getString(key: String): String =
        runBlocking {
            dataStore.data.map {
                it[stringPreferencesKey(key)]
            }.first() ?: ""
        }

    private fun writeString(key: String, value: String) {
        scope.launch {
            dataStore.edit { prefs ->
                prefs[stringPreferencesKey(key)] = value
            }
        }
    }

    private fun getBoolean(key: String): Boolean =
        runBlocking {
            withTimeoutOrNull(500L) {
                dataStore.data.map { prefs ->
                    prefs[booleanPreferencesKey(key)]
                }.first()
            } ?: false
        }

    private fun writeBoolean(key: String, value: Boolean) {
        scope.launch {
            dataStore.edit { prefs ->
                prefs[booleanPreferencesKey(key)] = value
            }
        }
    }

    private fun getLong(key: String): Long =
        runBlocking {
            withTimeoutOrNull(1000L) {
                dataStore.data.map { prefs ->
                    prefs[longPreferencesKey(key)]
                }.first()
            } ?: 0L
        }

    private fun writeLong(key: String, value: Long) {
        scope.launch {
            dataStore.edit { prefs ->
                prefs[longPreferencesKey(key)] = value
            }
        }
    }
}