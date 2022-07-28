package com.acidhand.currencyconverter.presentation.screen.state

import com.acidhand.currencyconverter.R
import com.acidhand.currencyconverter.presentation.models.Currency
import com.acidhand.currencyconverter.presentation.models.FilterOption

data class MainState(
    val listCurrency: List<Currency>,
    val listCurrencyFav: List<Currency>,
    val selectedCurrency: Currency,
    val listFilterOptions: List<FilterOption>,
    val isTickerDropMenuVisible: Boolean,
    val isFilterDropMenuVisible: Boolean,
    val isFavoriteListEmpty: Boolean
) {
    companion object {
        val INITIAL = MainState(
            selectedCurrency = Currency(
                idCurrency = "R00000",
                flag = R.drawable.rus,
                charCode = "RUR",
                name = "Российский рубль",
                nominal = "1",
                value = "1.0000",
                isFavorite = false
            ),
            listCurrency = emptyList(),
            listCurrencyFav = emptyList(),
            listFilterOptions = FilterOption.values().toList(),
            isTickerDropMenuVisible = false,
            isFilterDropMenuVisible = false,
            isFavoriteListEmpty = false
        )
    }
}
