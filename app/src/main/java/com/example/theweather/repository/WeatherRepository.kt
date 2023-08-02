package com.example.theweather.repository

import android.util.Log
import com.example.theweather.data.DataOrException
import com.example.theweather.model.ApiObject
import com.example.theweather.network.WeatherApi
import retrofit2.http.Query
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(latQuery: Double,lonQuery: Double): DataOrException<ApiObject,Boolean,Exception>{

        val response = try {
            api.getWeather(query1 = latQuery, query2 = lonQuery)
        }catch (e: Exception){
            Log.d("Exception Found", "getWeather: $e")
            return DataOrException(e = e)
        }

        Log.d("INSIDE", "getWeather: $response")

        return DataOrException(data = response)

    }
}