package com.sdk.weatherapp.presentation.ui.detail

import com.sdk.weatherapp.data.detail.dto.WeatherDataResponse
import retrofit2.Response

sealed class ApiState {
    object Loading : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    class Success(val data: Response<WeatherDataResponse>) : ApiState()
    object Init : ApiState()
}