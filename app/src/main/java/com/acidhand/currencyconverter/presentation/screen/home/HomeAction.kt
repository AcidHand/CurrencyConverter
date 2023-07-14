package com.acidhand.currencyconverter.presentation.screen.home

import com.acidhand.currencyconverter.presentation.models.Currency
import com.acidhand.currencyconverter.presentation.models.FilterOption

sealed class HomeAction {
    data class OnCurrencyFavoriteIconClick(val currency: Currency) : HomeAction()

    data class OnTickerDropDownItemClick(val currency: Currency) : HomeAction()
    object ShowTickerDropDownMenu : HomeAction()
    object CloseTickerDropDownMenu : HomeAction()

    data class OnFilterDropDownItemClick(val filterOption: FilterOption) : HomeAction()
    object ShowFilterDropDownMenu : HomeAction()
    object CloseFilterDropDownMenu : HomeAction()
}
