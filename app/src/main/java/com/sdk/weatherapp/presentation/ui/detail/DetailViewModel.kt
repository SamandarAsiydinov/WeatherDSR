package com.sdk.weatherapp.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdk.weatherapp.data.detail.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel() {
    private val _response: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Init)
    val response: StateFlow<ApiState> get() = _response

    fun getWeather(lat: String, lon: String, appId: String) = viewModelScope.launch {
        _response.value = ApiState.Loading
        detailRepository.getWeather(
            lat, lon, appId
        ).catch {
            _response.value = ApiState.Failure(it)
        }.collect { data ->
            _response.value = ApiState.Success(data)
        }
    }
}