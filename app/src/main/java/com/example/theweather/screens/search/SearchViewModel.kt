package com.example.theweather.screens.search

import androidx.lifecycle.ViewModel
import com.example.theweather.data.DataOrException
import com.example.theweather.model.Geocoding.GeocodingApiResponse
import com.example.theweather.repository.GeocodingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor( private val geocodingRepository: GeocodingRepository):ViewModel(){
    suspend fun getCoordinatesForCity(city: String): DataOrException<GeocodingApiResponse, Boolean, Exception> {
        return geocodingRepository.getCoordinatesForCity(city)
    }
}