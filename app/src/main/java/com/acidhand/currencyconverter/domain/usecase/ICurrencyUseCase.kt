package com.acidhand.currencyconverter.domain.usecase

import com.acidhand.currencyconverter.domain.models.CurrencyApi
import kotlinx.coroutines.flow.MutableStateFlow

interface ICurrencyUseCase {
    val responseCurrencyApi: MutableStateFlow<List<CurrencyApi>>
    fun fetchCurrency()
}