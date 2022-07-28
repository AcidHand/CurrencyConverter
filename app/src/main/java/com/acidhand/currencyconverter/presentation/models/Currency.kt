package com.acidhand.currencyconverter.presentation.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_tables")
data class Currency(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo val idCurrency: String,
    @ColumnInfo val flag: Int,
    @ColumnInfo val charCode: String,
    @ColumnInfo val nominal: String,
    @ColumnInfo val name: String,
    @ColumnInfo val value: String,
    @ColumnInfo val isFavorite: Boolean
)
