package com.acidhand.currencyconverter.presentation.screen.home.content

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.acidhand.currencyconverter.R
import com.acidhand.currencyconverter.presentation.screen.home.HomeAction
import com.acidhand.currencyconverter.presentation.screen.home.HomeState

@Composable
fun TopBar(state: HomeState, actions: (HomeAction) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary)
            .padding(vertical = 16.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.clickable { actions(HomeAction.ShowTickerDropDownMenu) }) {
            Text(
                text = stringResource(
                    id = R.string.top_bar_selected_currency,
                    state.selectedCurrency.nominal,
                    state.selectedCurrency.charCode
                )
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null
            )
            DropdownMenu(
                modifier = Modifier.background(Color.DarkGray),
                expanded = state.isTickerDropMenuVisible,
                onDismissRequest = { actions(HomeAction.CloseTickerDropDownMenu) }) {
                state.listCurrency.forEach {
                    DropdownMenuItem(onClick = {
                        actions(HomeAction.OnTickerDropDownItemClick(currency = it))
                    }) { Text(text = it.charCode) }
                }
            }
        }
        Row(modifier = Modifier.clickable { actions(HomeAction.ShowFilterDropDownMenu) }) {
            Icon(
                imageVector = Icons.Filled.FilterList,
                contentDescription = null
            )
            DropdownMenu(
                modifier = Modifier.background(Color.DarkGray),
                expanded = state.isFilterDropMenuVisible,
                onDismissRequest = { actions(HomeAction.CloseFilterDropDownMenu) }
            ) {
                state.listFilterOptions.forEach {
                    DropdownMenuItem(
                        onClick = { actions(HomeAction.OnFilterDropDownItemClick(filterOption = it)) }
                    ) { Text(text = stringResource(id = it.title)) }
                }
            }
        }
    }
}