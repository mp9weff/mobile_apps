package com.example.app.network

import com.example.app.BuildConfig
import com.example.app.data.WeatherApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val apiKeyInterceptor = Interceptor { chain ->
        val original = chain.request()
        val originalUrl = original.url
        val hasAppId = originalUrl.queryParameter("appid") != null
        val apiKey = BuildConfig.OPEN_WEATHER_API_KEY.ifEmpty { 
            "9060eca96c471f5d7ae9ab11f8286796" 
        }
        val url = if (hasAppId) {
            originalUrl
        } else {
            originalUrl.newBuilder()
                .addQueryParameter("appid", apiKey)
                .build()
        }
        val request = original.newBuilder().url(url).build()
        chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherApi: WeatherApi = retrofit.create(WeatherApi::class.java)
}

