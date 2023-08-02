package com.example.theweather.screens.main

import androidx.lifecycle.ViewModel
import com.example.theweather.data.DataOrException
import com.example.theweather.model.ApiObject

import com.example.theweather.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository):ViewModel(){

    suspend fun getWeatherData(lat: Double,lon: Double)
            : DataOrException<ApiObject, Boolean, Exception> {
        return repository.getWeather(latQuery = lat, lonQuery = lon)

    }




}