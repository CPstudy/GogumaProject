package com.cpstudy.gogumaproject.retro

data class Restaurant(
    val next_page_token: String,
    val results: List<Place>,
)

data class Place(
    val business_status: String,
    val formatted_address: String,
    val geometry: Geometry,
    val name: String,
    val rating: Double,
)

data class Geometry(
    val location: Location
)

data class Location(
    val lat: Double,
    val lng: Double,
)