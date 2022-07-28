package com.acidhand.currencyconverter.presentation.models

import androidx.annotation.StringRes
import com.acidhand.currencyconverter.R

enum class Slide(@StringRes val title: Int) {
    POPULAR(R.string.pager_slide_popular),
    FAVORITES(R.string.pager_slide_favourites)
}