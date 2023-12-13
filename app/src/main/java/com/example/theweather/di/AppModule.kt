package com.example.theweather.di

import android.content.Context
import androidx.room.Room
import com.example.theweather.network.GeocodingApi
import com.example.theweather.network.WeatherApi
import com.example.theweather.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherApi{
        return Retrofit.Builder()
               .baseUrl(Constants.WEATHER_BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
               .build()
               .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGeocoderApi(): GeocodingApi {
        return Retrofit.Builder()
            .baseUrl(Constants.GEOCODING_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeocodingApi::class.java)
    }
}