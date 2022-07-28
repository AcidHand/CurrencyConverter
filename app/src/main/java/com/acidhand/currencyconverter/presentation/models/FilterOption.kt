package com.acidhand.currencyconverter.presentation.models

import androidx.annotation.StringRes
import com.acidhand.currencyconverter.R

enum class FilterOption(@StringRes val title: Int) {
    ALPHABET_INCREASE(R.string.filter_option_alphabet_increase),
    ALPHABET_DECREASE(R.string.filter_option_alphabet_decrease),
    VALUE_INCREASE(R.string.filter_option_value_increase),
    VALUE_DECREASE(R.string.filter_option_value_decrease)
}