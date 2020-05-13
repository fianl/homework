package com.kkc.idushomework.network

import com.kkc.idushomework.data.entity.City
import com.kkc.idushomework.data.entity.WeatherResult
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    @GET("location/search")
    fun searchCity(@Query("query") query: String): Single<ArrayList<City>>

    @GET("location/{woeid}")
    fun getCityWeatehrs(@Path("woeid") cityId: String): Single<WeatherResult>
}