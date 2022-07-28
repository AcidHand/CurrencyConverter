package com.acidhand.currencyconverter.data

import android.content.Context
import com.acidhand.currencyconverter.domain.models.CurrencyApi
import com.acidhand.currencyconverter.domain.usecase.ICurrencyUseCase
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONException

class CurrencyUseCaseImpl(val context: Context) : ICurrencyUseCase {

    override val responseCurrencyApi = MutableStateFlow<List<CurrencyApi>>(emptyList())

    override fun fetchCurrency() {
        val currencyListResponse = mutableListOf<CurrencyApi>()
        val requestQueue = Volley.newRequestQueue(context)
        val url = "https://www.cbr-xml-daily.ru/daily_json.js"
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val jsonValute = response.getJSONObject("Valute")
                    val jsonKey = jsonValute.keys()

                    while (jsonKey.hasNext()) {
                        val key = jsonKey.next()
                        val jsonValuteItem = jsonValute.getJSONObject(key)

                        val id = jsonValuteItem.getString("ID")
                        val charCode = jsonValuteItem.getString("CharCode")
                        val nominal = jsonValuteItem.getString("Nominal")
                        val name = jsonValuteItem.getString("Name")
                        val value = jsonValuteItem.getString("Value")

                        val currency = CurrencyApi(
                            id = id,
                            charCode = charCode,
                            nominal = nominal,
                            name = name,
                            value = value,
                            isFavorite = false
                        )
                        currencyListResponse.add(currency)
                    }
                    responseCurrencyApi.value = currencyListResponse
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }) { error -> error.printStackTrace() }
        requestQueue.add(request)
    }
}