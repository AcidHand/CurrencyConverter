package com.acidhand.currencyconverter.data.database.dao

import androidx.room.*
import com.acidhand.currencyconverter.presentation.models.Currency

@Dao
interface AppDao {

    @Query("SELECT * FROM favorites_tables ORDER BY id DESC")
    fun getRecords(): List<Currency>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecord(currency: Currency)

    @Query("DELETE FROM favorites_tables WHERE idCurrency = :idCurrency")
    fun deleteRecord(idCurrency: String)
}