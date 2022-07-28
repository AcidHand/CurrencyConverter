package com.acidhand.currencyconverter.data.database.repository

import com.acidhand.currencyconverter.data.database.dao.AppDao
import com.acidhand.currencyconverter.presentation.models.Currency
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor (private val appDao: AppDao):IRoomRepository {

    override fun getRecords(): List<Currency>{
        return appDao.getRecords()
    }
    override suspend fun insert(currency: Currency){
        appDao.insertRecord(currency)

    }
    override suspend fun delete(idCurrency: String){
        appDao.deleteRecord(idCurrency)
    }
}