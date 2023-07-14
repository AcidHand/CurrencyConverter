package com.acidhand.currencyconverter.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acidhand.currencyconverter.data.database.repository.IRoomRepository
import com.acidhand.currencyconverter.domain.usecase.ICurrencyUseCase
import com.acidhand.currencyconverter.presentation.models.Currency
import com.acidhand.currencyconverter.presentation.models.FilterOption
import com.acidhand.currencyconverter.utils.CurrencyMapper
import com.acidhand.currencyconverter.utils.errorEvent
import com.acidhand.currencyconverter.utils.navigationEvent
import com.acidhand.currencyconverter.utils.provideDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    useCase: ICurrencyUseCase,
    private val repository: IRoomRepository,
    private val mapper: CurrencyMapper
) : ViewModel() {
    private val _homeState = MutableStateFlow(HomeState.INITIAL)
    private var homeState: HomeState by _homeState
    val uiState = _homeState.asStateFlow()

    init {
        useCase.fetchCurrency()
        useCase.responseCurrencyApi
            .onEach {
                val listRoom = mapper.mapToCurrencyFavoriteList(listRoom = repository.getRecords())
                val list = mapper.mapCurrencyApiToCurrency(currency = it, listRoom = listRoom)
                homeState = homeState.copy(
                    listCurrencyFav = listRoom,
                    listCurrency = list,
                    isFavoriteListEmpty = listRoom.isEmpty()
                )
            }
            .launchIn(viewModelScope)
    }

    fun actions(action: HomeAction) {
        when (action) {
            is HomeAction.OnCurrencyFavoriteIconClick -> {
                onCurrencyFavoriteIconClicked(currency = action.currency)
            }

            is HomeAction.OnTickerDropDownItemClick -> {
                onTickerDropDownItemClicked(selectedCurrency = action.currency)
            }

            HomeAction.ShowTickerDropDownMenu -> homeState =
                homeState.copy(isTickerDropMenuVisible = true)

            HomeAction.CloseTickerDropDownMenu -> homeState =
                homeState.copy(isTickerDropMenuVisible = false)

            is HomeAction.OnFilterDropDownItemClick -> onFilterDropDownItemClicked(
                filterOption = action.filterOption
            )

            HomeAction.ShowFilterDropDownMenu -> homeState =
                homeState.copy(isFilterDropMenuVisible = true)

            HomeAction.CloseFilterDropDownMenu -> homeState =
                homeState.copy(isFilterDropMenuVisible = false)
        }
    }

    private fun onCurrencyFavoriteIconClicked(currency: Currency) {
        val index = homeState.listCurrency.indexOf(currency)
        val listCurrency = homeState.listCurrency.toMutableList()

        homeState.listCurrency[index]
            .let {
                if (!it.isFavorite) insertRecord(currency = currency)
                else deleteRecord(id = currency.idCurrency)
                it.copy(isFavorite = !it.isFavorite)
            }
            .run { listCurrency[index] = this }

        val listCurrencyFav = listCurrency.filter { it.isFavorite }
        homeState = homeState.copy(
            listCurrency = listCurrency,
            listCurrencyFav = listCurrencyFav,
            isFavoriteListEmpty = listCurrencyFav.isEmpty()
        )
    }

    private fun onTickerDropDownItemClicked(selectedCurrency: Currency) {
        val listCurrency = mapper.mapListUponSelectedCurrency(
            currencyList = homeState.listCurrency,
            selectedCurrency = selectedCurrency
        )
        val listCurrencyFav = mapper.mapListUponSelectedCurrency(
            currencyList = homeState.listCurrencyFav,
            selectedCurrency = selectedCurrency
        )
        homeState = homeState.copy(
            selectedCurrency = selectedCurrency,
            isTickerDropMenuVisible = false,
            listCurrency = listCurrency,
            listCurrencyFav = listCurrencyFav
        )
    }

    private fun onFilterDropDownItemClicked(filterOption: FilterOption) {
        val listCurrencyFiltered = when (filterOption) {
            FilterOption.ALPHABET_INCREASE -> homeState.listCurrency.sortedBy { it.charCode }
            FilterOption.ALPHABET_DECREASE -> homeState.listCurrency.sortedByDescending { it.charCode }
            FilterOption.VALUE_INCREASE -> homeState.listCurrency.sortedBy { it.value }
            else -> homeState.listCurrency.sortedByDescending { it.value }
        }
        homeState = homeState.copy(
            isFilterDropMenuVisible = false,
            listCurrency = listCurrencyFiltered
        )
    }

    private fun insertRecord(currency: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(currency = currency)
        }
    }

    private fun deleteRecord(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(idCurrency = id)
        }
    }
}