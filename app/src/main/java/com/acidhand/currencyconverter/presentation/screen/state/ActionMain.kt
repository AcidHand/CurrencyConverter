package com.acidhand.currencyconverter.presentation.screen.state

import com.acidhand.currencyconverter.presentation.models.Currency
import com.acidhand.currencyconverter.presentation.models.FilterOption

sealed class ActionMain {
    data class OnCurrencyFavoriteIconClick(val currency: Currency) : ActionMain()

    data class OnTickerDropDownItemClick(val currency: Currency): ActionMain()
    object ShowTickerDropDownMenu: ActionMain()
    object CloseTickerDropDownMenu: ActionMain()

    data class OnFilterDropDownItemClick(val filterOption: FilterOption): ActionMain()
    object ShowFilterDropDownMenu: ActionMain()
    object CloseFilterDropDownMenu: ActionMain()
}