package com.sanal.weatherapp.network

import com.sanal.weatherapp.data.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("v1/current.json")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String,
        @Query("q") city: String
    ): WeatherResponse

    companion object {
        const val BASE_URL = "" /* https://www.weatherapi.com/ */
    }
}