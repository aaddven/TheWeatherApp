package com.example.theweather.model.Weather

data class WeatherApiResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherItem>,
    val message: Int
)