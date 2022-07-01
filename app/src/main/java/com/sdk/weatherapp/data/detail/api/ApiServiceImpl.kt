package com.sdk.weatherapp.data.detail.api

import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {
    suspend fun getWeather(
        lat: String,
        lon: String,
        appId: String
    ) = apiService.getWeather(lat, lon, appId)
}