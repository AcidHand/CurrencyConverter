package com.acidhand.currencyconverter.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acidhand.currencyconverter.data.database.repository.IRoomRepository
import com.acidhand.currencyconverter.domain.usecase.ICurrencyUseCase
import com.acidhand.currencyconverter.presentation.models.Currency
import com.acidhand.currencyconverter.presentation.models.FilterOption
import com.acidhand.currencyconverter.presentation.screen.state.ActionMain
import com.acidhand.currencyconverter.presentation.screen.state.MainState
import com.acidhand.currencyconverter.utils.CurrencyMapper
import com.acidhand.currencyconverter.utils.provideDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    useCase: ICurrencyUseCase,
    private val repository: IRoomRepository,
    private val mapper: CurrencyMapper
) : ViewModel() {

    private val _state = MutableStateFlow(MainState.INITIAL)
    private var state: MainState by _state
    val uiState = _state.asStateFlow()

    init {
        useCase.fetchCurrency()
        useCase.responseCurrencyApi
            .onEach {
                val listRoom = mapper.mapToCurrencyFavoriteList(listRoom = repository.getRecords())
                val list = mapper.mapCurrencyApiToCurrency(currency = it, listRoom = listRoom)
                state = state.copy(
                    listCurrencyFav = listRoom,
                    listCurrency = list,
                    isFavoriteListEmpty = listRoom.isEmpty()
                )
            }
            .launchIn(viewModelScope)
    }

    fun actions(action: ActionMain) {
        when (action) {
            is ActionMain.OnCurrencyFavoriteIconClick -> {
                onCurrencyFavoriteIconClicked(currency = action.currency)
            }
            is ActionMain.OnTickerDropDownItemClick -> {
                onTickerDropDownItemClicked(selectedCurrency = action.currency)
            }
            ActionMain.ShowTickerDropDownMenu -> state = state.copy(isTickerDropMenuVisible = true)
            ActionMain.CloseTickerDropDownMenu -> state =
                state.copy(isTickerDropMenuVisible = false)
            is ActionMain.OnFilterDropDownItemClick -> onFilterDropDownItemClicked(
                filterOption = action.filterOption
            )
            ActionMain.ShowFilterDropDownMenu -> state = state.copy(isFilterDropMenuVisible = true)
            ActionMain.CloseFilterDropDownMenu -> state =
                state.copy(isFilterDropMenuVisible = false)
        }
    }

    private fun onCurrencyFavoriteIconClicked(currency: Currency) {
        val index = state.listCurrency.indexOf(currency)
        val listCurrency = state.listCurrency.toMutableList()

        state.listCurrency[index]
            .let {
                if (!it.isFavorite) {
                    insertRecord(currency = currency)
                } else {
                    deleteRecord(id = currency.idCurrency)
                }
                it.copy(isFavorite = !it.isFavorite)
            }
            .run { listCurrency[index] = this }

        val listCurrencyFav = listCurrency.filter { it.isFavorite }
        state = state.copy(
            listCurrency = listCurrency,
            listCurrencyFav = listCurrencyFav,
            isFavoriteListEmpty = listCurrencyFav.isEmpty()
        )
    }

    private fun onTickerDropDownItemClicked(selectedCurrency: Currency) {
        val listCurrency = mapper.mapListUponSelectedCurrency(
            currencyList = state.listCurrency,
            selectedCurrency = selectedCurrency
        )
        val listCurrencyFav = mapper.mapListUponSelectedCurrency(
            currencyList = state.listCurrencyFav,
            selectedCurrency = selectedCurrency
        )

        state = state.copy(
            selectedCurrency = selectedCurrency,
            isTickerDropMenuVisible = false,
            listCurrency = listCurrency,
            listCurrencyFav = listCurrencyFav
        )
    }

    private fun onFilterDropDownItemClicked(filterOption: FilterOption) {
        val listCurrencyFiltered = when (filterOption) {
            FilterOption.ALPHABET_INCREASE -> state.listCurrency.sortedBy { it.charCode }
            FilterOption.ALPHABET_DECREASE -> state.listCurrency.sortedByDescending { it.charCode }
            FilterOption.VALUE_INCREASE -> state.listCurrency.sortedBy { it.value }
            else -> state.listCurrency.sortedByDescending { it.value }
        }
        state = state.copy(
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