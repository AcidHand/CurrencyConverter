package com.acidhand.currencyconverter.utils.resourse_provider

import android.content.res.Resources

class ResourceProviderImpl(private val resources: Resources) : IResourceProvider {

    override fun getString(res: Int, vararg args: Any): String {
        return resources.getString(res, *args)
    }
}