package com.example.cntrgssr.core.data.api

import com.example.cntrgssr.core.data.dto.CountryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CountryApi {
    @GET("v1/country")
    suspend fun getCountry(
        @Query("name") name: String,
    ): List<CountryResponse>
}