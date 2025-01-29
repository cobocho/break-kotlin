package com.example.shoppingcart

data class LocationData(
    val lat: Double,
    val lng: Double
)

data class GeocodingResponse(
    val results: List<GeocodingResult>,
    val status: String
)

data class GeocodingResult(
    val formatted_address: String
)