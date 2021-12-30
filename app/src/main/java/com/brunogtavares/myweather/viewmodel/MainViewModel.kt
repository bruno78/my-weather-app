package com.brunogtavares.myweather.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brunogtavares.myweather.model.WeatherLocation
import com.brunogtavares.myweather.repository.MyWeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MyWeatherRepository
) : ViewModel() {

    val screenState : MutableState<MainScreenState> = mutableStateOf(MainScreenState.Loading)


    fun onLoadWeather(city: String) {
        viewModelScope.launch(Dispatchers.Main) {
            if (city.isEmpty()) return@launch
            val response = repository.getMyWeatherForecastAsync(city).await()

            screenState.value = when {
                response.isSuccessful -> {
                    Log.d("GET", "MyWeather response: ${response.body().toString()}")
                    MainScreenState.LoadSuccess(requireNotNull(response.body()){"Response body cannot be null"})
                }
                else -> MainScreenState.LoadFailed(response.message())
            }
        }
    }
}

sealed class MainScreenState {
    object Loading : MainScreenState()
    data class LoadSuccess(val data: WeatherLocation) : MainScreenState()
    data class LoadFailed(val error: String) : MainScreenState()
}