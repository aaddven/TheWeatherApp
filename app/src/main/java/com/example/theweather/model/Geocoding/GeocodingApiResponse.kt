package com.example.theweather.model.Geocoding

data class GeocodingApiResponse(
    val results: List<GeocodingResult>
)

data class GeocodingResult(
    val geometry: GeocodingGeometry
)

data class GeocodingGeometry(
    val lat: Double,
    val lng: Double
)