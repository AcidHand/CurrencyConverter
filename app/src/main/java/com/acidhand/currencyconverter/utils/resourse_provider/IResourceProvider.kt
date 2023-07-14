package com.acidhand.currencyconverter.utils.resourse_provider

import androidx.annotation.StringRes

interface IResourceProvider {
    fun getString(@StringRes res: Int, vararg args: Any): String
}