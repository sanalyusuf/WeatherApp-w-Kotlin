package com.sanal.weatherapp.data


import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val location: Location,
    val current: Current
)

data class Location(
    val name: String,
    val region: String,
    val country: String
)

data class Current(
    @SerializedName("temp_c") val tempC: Double,
    val condition: Condition,
    @SerializedName("wind_kph") val windKph: Double,
    @SerializedName("humidity") val humidity: Int
)

data class Condition(
    val text: String,
    val icon: String
)