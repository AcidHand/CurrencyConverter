package com.acidhand.currencyconverter.data.database.repository

import com.acidhand.currencyconverter.presentation.models.Currency

interface IRoomRepository {
    fun getRecords(): List<Currency>
    suspend fun insert(currency: Currency)
    suspend fun delete(idCurrency: String)
}
