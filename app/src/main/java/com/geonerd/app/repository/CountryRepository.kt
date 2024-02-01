package com.geonerd.app.repository

import com.geonerd.app.api.CountriesApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CountryRepository {
    private val api: CountriesApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        api = retrofit.create(CountriesApi::class.java)
    }

    suspend fun getAllCountries() = api.getAllCountries()

    suspend fun getFlagSvg(url: String): String? {
        val response = api.getFlagSvg(url)
        var flag: String? = null
        if (response.isSuccessful) {
            flag = response.body()?.string()
        }

        return flag
    }
}
