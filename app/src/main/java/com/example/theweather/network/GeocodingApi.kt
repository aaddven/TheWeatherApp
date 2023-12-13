package com.example.theweather.network

import com.example.theweather.model.Geocoding.GeocodingApiResponse
import com.example.theweather.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface GeocodingApi {
    @GET("json")
    suspend fun getCoordinates(
    @Query("q") query: String,
    @Query("key") apiKey: String = Constants.GEOCODING_API_KEY    ): GeocodingApiResponse
}