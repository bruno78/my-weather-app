package com.brunogtavares.myweather.network

import com.brunogtavares.myweather.API_KEY
import com.brunogtavares.myweather.model.WeatherLocation
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface MyWeatherApi {

    @GET("data/2.5/forecast/daily")
    fun getMyWeatherForecastAsync(
        @Query("q") city: String,
        @Query("units") units: String = "imperial",
        @Query("cnt") count: String = "7",
        @Query("appid") appid: String = API_KEY,
    ): Deferred<Response<WeatherLocation>>
}