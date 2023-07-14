package com.acidhand.currencyconverter.presentation.screen.home.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.acidhand.currencyconverter.R
import com.acidhand.currencyconverter.presentation.compose.theme.CurrencyConverterTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import com.acidhand.currencyconverter.presentation.models.Currency
import com.acidhand.currencyconverter.presentation.models.Slide
import com.acidhand.currencyconverter.presentation.screen.home.HomeAction
import com.acidhand.currencyconverter.presentation.screen.home.HomeState

private val homeStatePreview = HomeState(
    listCurrency = listOf(
        Currency(
            idCurrency = "R00000",
            flag = R.drawable.aud,
            charCode = "AUD",
            name = "",
            nominal = "1",
            value = "45.4324",
            isFavorite = false
        ),
        Currency(
            idCurrency = "",
            flag = R.drawable.eur,
            charCode = "EUR",
            name = "",
            nominal = "",
            value = "60.3245423",
            isFavorite = true
        ),
        Currency(
            idCurrency = "",
            flag = R.drawable.usd,
            charCode = "USD",
            name = "",
            nominal = "",
            value = "53.2456",
            isFavorite = false
        )
    ),
    listCurrencyFav = emptyList(),
    selectedCurrency = Currency(
        idCurrency = "R00000",
        flag = R.drawable.rus,
        charCode = "RUR",
        name = "Российский рубль",
        nominal = "1",
        value = "1.0000",
        isFavorite = false
    ),
    listFilterOptions = emptyList(),
    isTickerDropMenuVisible = false,
    isFilterDropMenuVisible = false,
    isFavoriteListEmpty = false,
)

@Preview
@Composable
private fun Preview() {
    CurrencyConverterTheme {
        Surface {
            HomeContent(state = homeStatePreview, actions = {})
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeContent(state: HomeState, actions: (HomeAction) -> Unit) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary)
                .padding(bottom = 10.dp)
        ) {
            val pagerState = rememberPagerState()
            val coroutineScope = rememberCoroutineScope()

            TabRow(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.BottomCenter)
                    .background(MaterialTheme.colors.primary),
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = Color.Transparent,
                contentColor = Color.Transparent,
            ) {
                Slide.values().forEachIndexed { index, slide ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(page = index)
                            }
                        },
                        selectedContentColor = Color.Black,
                        unselectedContentColor = Color.Black,
                    ) {
                        Box(
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                                .background(
                                    color =
                                    if (pagerState.currentPage == index) MaterialTheme.colors.primaryVariant
                                    else Color.LightGray, shape = RoundedCornerShape(4.dp)
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = stringResource(slide.title),
                                style = MaterialTheme.typography.button
                            )
                        }
                    }
                }
            }
            HorizontalPager(
                count = Slide.values().size,
                state = pagerState,
                modifier = Modifier.padding(bottom = 50.dp)
            ) { page ->
                when (Slide.values()[page]) {
                    Slide.POPULAR -> {
                        LazyColumn(
                            modifier = Modifier
                                .background(MaterialTheme.colors.background)
                                .padding(bottom = 16.dp)
                        ) {
                            items(state.listCurrency) {
                                CurrencyItem(currency = it,
                                    onClick = {
                                        actions(
                                            HomeAction.OnCurrencyFavoriteIconClick(currency = it)
                                        )
                                    })
                            }
                        }
                    }

                    Slide.FAVORITES -> {
                        if (state.isFavoriteListEmpty) EmptyFavoritesContent()
                        else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = MaterialTheme.colors.background)
                                    .padding(bottom = 16.dp)
                            ) {
                                items(state.listCurrencyFav) {
                                    CurrencyItem(currency = it,
                                        onClick = {
                                            actions(
                                                HomeAction.OnCurrencyFavoriteIconClick(currency = it)
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CurrencyItem(currency: Currency, onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = currency.flag),
                modifier = Modifier
                    .size(60.dp)
                    .padding(start = 16.dp),
                contentDescription = null,
            )
            Text(
                text = currency.nominal,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(50.dp),
            )
            Text(text = currency.charCode)
            Text(text = currency.value)
            IconButton(onClick = { onClick() }) {
                Icon(
                    imageVector =
                    if (currency.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    tint = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.padding(end = 16.dp),
                    contentDescription = null
                )
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Color.DarkGray)
        )
    }
}

@Composable
private fun EmptyFavoritesContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.no_favorite_currency),
                color = Color.Black.copy(alpha = 0.3f),
            )
            Icon(
                imageVector = Icons.Filled.Paid,
                modifier = Modifier.size(200.dp),
                tint = Color.Black.copy(alpha = 0.3f),
                contentDescription = null
            )
        }
    }
}
