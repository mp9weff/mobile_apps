package com.example.app.data

import com.example.app.network.RetrofitClient

class WeatherRepository {
    private val api = RetrofitClient.weatherApi

    suspend fun getCurrentWeather(city: String): Result<WeatherResponse> {
        return try {
            Result.success(api.getCurrentWeather(city))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getForecast(city: String): Result<ForecastResponse> {
        return try {
            Result.success(api.getForecast(city))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

