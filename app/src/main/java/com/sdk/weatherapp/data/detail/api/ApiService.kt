package com.sdk.weatherapp.data.detail.api

import com.sdk.weatherapp.data.detail.dto.WeatherDataResponse
import com.sdk.weatherapp.data.util.Constants.END_POINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(END_POINT)
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String
    ): Response<WeatherDataResponse>
}