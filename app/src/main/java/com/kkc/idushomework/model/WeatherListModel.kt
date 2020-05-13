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

    private val _toaseMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String>
        get() = _toaseMessage

    private val _citys = MutableLiveData<ArrayList<City>>()
    private val _wocList = MutableLiveData<ArrayList<WeatherOfCity>>()
    val wocList: LiveData<ArrayList<WeatherOfCity>>
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
                _citys.value = result
                val sources = ArrayList<Single<WeatherResult>>()
                result.forEach { city -> sources.add(requestCityWeather(city.woeid)) }
                cityWeatherZip(sources)
            }, {err ->
                err.printStackTrace()
                _showProgress.value = false
            })
    }

    private fun requestCityWeather(cityId: String) = Retrofit.getInstance().getWeatherService().getCityWeatehrs(cityId)

    private fun cityWeatherZip(list: ArrayList<Single<WeatherResult>>) {
        SingleZipArray(list.toTypedArray(),
            Function<Array<Any>, ArrayList<WeatherResult>> { t ->
                val resultList = ArrayList<WeatherResult>()
                t?.forEach { item -> resultList.add(item as WeatherResult) }
                resultList
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { list -> list.map { weathers -> WeatherOfCity("", weathers.consolidated_weather[0], weathers.consolidated_weather[1]) }  }
            .subscribe({result ->
                _showProgress.value = false
                result.forEachIndexed { index, woc -> woc.title = _citys.value?.get(index)?.title }
                _wocList.value = result as ArrayList<WeatherOfCity>?
            }, {err ->
                err.printStackTrace()
                _showProgress.value = false
            })

    }
}