package com.brunogtavares.myweather.model

data class WeatherDescription(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)