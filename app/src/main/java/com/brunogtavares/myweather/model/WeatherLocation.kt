package com.brunogtavares.myweather.model

data class WeatherLocation(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherInfo>,
    val message: Double
)