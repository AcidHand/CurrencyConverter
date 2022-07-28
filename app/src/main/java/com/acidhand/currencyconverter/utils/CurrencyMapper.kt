package com.acidhand.currencyconverter.utils

import com.acidhand.currencyconverter.R
import com.acidhand.currencyconverter.domain.models.CurrencyApi
import com.acidhand.currencyconverter.presentation.models.Currency
import com.acidhand.currencyconverter.presentation.models.Flags
import javax.inject.Inject

class CurrencyMapper @Inject constructor() {
    fun mapCurrencyApiToCurrency(
        currency: List<CurrencyApi>,
        listRoom: List<Currency>
    ): List<Currency> {
        val list = currency.toMutableList()
        list.add(
            CurrencyApi(
                id = "R0000",
                charCode = "RUR",
                nominal = "1",
                name = "Российский рубль",
                value = "1.000",
                isFavorite = false
            )
        )
        val newList = list.map {
            with(it) {
                Currency(
                    idCurrency = id,
                    flag = provideFlag(id),
                    charCode = charCode,
                    nominal = nominal,
                    name = name,
                    value = value,
                    isFavorite = false
                )
            }
        }
        return mapListUponFavoriteCurrency(listMain = newList, listRoom = listRoom)
    }

    fun mapListUponSelectedCurrency(
        currencyList: List<Currency>,
        selectedCurrency: Currency
    ): List<Currency> {
        val list = currencyList.map {
            val crossValue = (it.value.toDouble() / selectedCurrency.value.toDouble()).toString()
            val value = when {
                crossValue.length > 6 -> {
                    crossValue.slice(0..5)
                }
                selectedCurrency.idCurrency == it.idCurrency -> {
                    crossValue.plus("000")
                }
                else -> {
                    crossValue
                }
            }
            Currency(
                idCurrency = it.idCurrency,
                flag = it.flag,
                charCode = it.charCode,
                name = it.name,
                nominal = it.nominal,
                value = value,
                isFavorite = it.isFavorite
            )
        }
        return list
    }

    fun mapToCurrencyFavoriteList(listRoom: List<Currency>): List<Currency> {
        return listRoom.map {
            Currency(
                idCurrency = it.idCurrency,
                flag = it.flag,
                charCode = it.charCode,
                name = it.name,
                nominal = it.nominal,
                value = it.value,
                isFavorite = true
            )
        }
    }

    private fun mapListUponFavoriteCurrency(
        listMain: List<Currency>,
        listRoom: List<Currency>
    ): List<Currency> {
        val list = listMain.map { currency ->
            val isFavorite =
                listRoom.firstOrNull { it.idCurrency == currency.idCurrency }?.isFavorite ?: false

            Currency(
                idCurrency = currency.idCurrency,
                flag = currency.flag,
                charCode = currency.charCode,
                name = currency.name,
                nominal = currency.nominal,
                value = currency.value,
                isFavorite = isFavorite
            )
        }

        return list
    }

    private fun provideFlag(id: String): Int {
        return Flags.values().firstOrNull { id == it.id }?.flag ?: R.drawable.default_flag
    }
}