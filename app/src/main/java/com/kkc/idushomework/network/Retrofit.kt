package com.kkc.idushomework.network

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {
    companion object {
        private var instance: Retrofit? = null

        fun getInstance(): Retrofit {
            if (instance == null)
                instance = Retrofit()

            return instance!!
        }
    }

    private var weatherService: WeatherService

    init {
        val baseUrl = "https://www.metaweather.com/api/"
        val apiRetrofit = retrofit2.Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        weatherService = apiRetrofit.create(WeatherService::class.java)
    }

    fun getWeatherService() = weatherService
}