package com.acidhand.currencyconverter.presentation.screen.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.acidhand.currencyconverter.R
import com.acidhand.currencyconverter.presentation.screen.state.ActionMain
import com.acidhand.currencyconverter.presentation.screen.state.MainState

@Composable
fun TopBar(uiState: MainState, actions: (ActionMain) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Row(modifier = Modifier.clickable { actions(ActionMain.ShowTickerDropDownMenu) }) {
            Text(
                text = stringResource(
                    id = R.string.top_bar_selected_currency,
                    uiState.selectedCurrency.nominal,
                    uiState.selectedCurrency.charCode
                )
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null
            )
            DropdownMenu(
                expanded = uiState.isTickerDropMenuVisible,
                onDismissRequest = { actions(ActionMain.CloseTickerDropDownMenu) }) {
                uiState.listCurrency.forEach {
                    DropdownMenuItem(onClick = {
                        actions(
                            ActionMain.OnTickerDropDownItemClick(
                                currency = it
                            )
                        )
                    }) {
                        Text(text = it.charCode)
                    }
                }
            }
        }
        Row(modifier = Modifier.clickable { actions(ActionMain.ShowFilterDropDownMenu) }) {
            Icon(
                imageVector = Icons.Filled.FilterList,
                contentDescription = null
            )
            DropdownMenu(
                expanded = uiState.isFilterDropMenuVisible,
                onDismissRequest = { actions(ActionMain.CloseFilterDropDownMenu) }) {
                uiState.listFilterOptions.forEach {
                    DropdownMenuItem(onClick = {
                        actions(
                            ActionMain.OnFilterDropDownItemClick(
                                filterOption = it
                            )
                        )
                    }) {
                        Text(text = stringResource(id = it.title))
                    }
                }
            }
        }
    }
}