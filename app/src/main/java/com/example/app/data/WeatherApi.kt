package com.example.app.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "uk"
    ): WeatherResponse

    @GET("forecast")
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "uk"
    ): ForecastResponse
}

