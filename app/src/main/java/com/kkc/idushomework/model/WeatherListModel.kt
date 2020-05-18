package com.kkc.idushomework.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kkc.idushomework.data.entity.City
import com.kkc.idushomework.data.entity.WeatherResult
import com.kkc.idushomework.network.Retrofit
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function
import io.reactivex.rxjava3.internal.operators.single.SingleZipArray
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherListModel {
    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean>
        get() = _isRefreshing

    private val _cities = ArrayList<City>()
    private val _wocList = MutableLiveData<List<WeatherOfCity>>()
    val wocList: LiveData<List<WeatherOfCity>>
        get() = _wocList

    init {
        _showProgress.value = true
        requestCityList()
    }

    private fun requestCityList() {
        Retrofit.getInstance().getWeatherService().searchCity("se")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({result ->
                _cities.clear()
                _cities.addAll(result)
                cityWeatherZip(result)
            }, {err ->
                err.printStackTrace()
                _showProgress.value = false
                _isRefreshing.value = false
            })
    }

    private fun requestCityWeather(cityId: String) = Retrofit.getInstance().getWeatherService().getCityWeatehrs(cityId)

    private fun cityWeatherZip(cityList: ArrayList<City>) {
        val sources = ArrayList<Single<WeatherResult>>()
        cityList.forEach { city -> sources.add(requestCityWeather(city.woeid)) }

        SingleZipArray(sources.toTypedArray(),
            Function<Array<Any>, ArrayList<WeatherResult>> { array ->
                val resultList = ArrayList<WeatherResult>()
                array?.forEach { item -> resultList.add(item as WeatherResult) }
                resultList
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { list -> list.mapIndexed { index, weatherResult -> WeatherOfCity(cityList[index].title, weatherResult.consolidated_weather[0], weatherResult.consolidated_weather[1]) } }
            .subscribe({result ->
                _showProgress.value = false
                _isRefreshing.value = false
                _wocList.value = result
            }, {err ->
                err.printStackTrace()
                _showProgress.value = false
                _isRefreshing.value = false
            })
    }

    fun requestRefresh() {
        _isRefreshing.value = true
        _wocList.value = null
        requestCityList()
    }
}