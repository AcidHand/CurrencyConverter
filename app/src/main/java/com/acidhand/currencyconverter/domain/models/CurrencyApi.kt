package com.acidhand.currencyconverter.domain.models

data class CurrencyApi(
    var id: String,
    var charCode: String,
    var nominal: String,
    var name: String,
    var value: String,
    val isFavorite: Boolean
)