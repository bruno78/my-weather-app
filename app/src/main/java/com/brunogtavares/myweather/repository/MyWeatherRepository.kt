package com.brunogtavares.myweather.repository

import com.brunogtavares.myweather.model.WeatherLocation
import com.brunogtavares.myweather.network.MyWeatherApi
import kotlinx.coroutines.Deferred
import retrofit2.Response
import javax.inject.Inject

interface MyWeatherRepository {
    fun getMyWeatherForecastAsync(
        city: String,
    ): Deferred<Response<WeatherLocation>>
}

class MyWeatherRepositoryImpl @Inject constructor(
    private val api: MyWeatherApi
) : MyWeatherRepository {
    override fun getMyWeatherForecastAsync(city: String): Deferred<Response<WeatherLocation>> =
        api.getMyWeatherForecastAsync(city)
}