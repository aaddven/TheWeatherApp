package com.example.theweather.repository

import android.util.Log
import com.example.theweather.data.DataOrException
import com.example.theweather.model.Geocoding.GeocodingApiResponse
import com.example.theweather.network.GeocodingApi
import com.example.theweather.utils.Constants
import javax.inject.Inject

class GeocodingRepository @Inject constructor(private val geocodingApi: GeocodingApi) {

    suspend fun getCoordinatesForCity(city: String): DataOrException<GeocodingApiResponse, Boolean, Exception> {

        val response = try {
            geocodingApi.getCoordinates(query = city)
        }catch (e: Exception){
            Log.d("Exception Found", "getCoordinates: $e")
            return DataOrException(e = e)
        }

        Log.d("INSIDE", "getCoordinates: $response")

        return DataOrException(data = response)

    }
}