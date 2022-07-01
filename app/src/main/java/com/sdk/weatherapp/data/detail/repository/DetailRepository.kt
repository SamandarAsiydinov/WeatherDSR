package com.sdk.weatherapp.data.detail.repository

import com.sdk.weatherapp.data.detail.api.ApiServiceImpl
import com.sdk.weatherapp.data.detail.dto.WeatherDataResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class DetailRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl) {
    fun getWeather(lat: String, lon: String, appId: String): Flow<Response<WeatherDataResponse>> = flow {
        emit(apiServiceImpl.getWeather(lat, lon, appId))
    }.flowOn(Dispatchers.IO)
}