package com.geonerd.app.api

import com.geonerd.app.model.Country
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


interface CountriesApi {
    @GET("v3.1/all?fields=name,flags,capital")
    suspend fun getAllCountries(): List<Country>

    @GET
    suspend fun getFlagSvg(@Url url: String): Response<ResponseBody>
}
