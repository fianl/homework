package com.kkc.idushomework.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kkc.idushomework.model.WeatherListModel
import com.kkc.idushomework.model.WeatherOfCity

class WeatherListViewModel : ViewModel() {
    private val model = WeatherListModel()

    val showProgress: LiveData<Boolean>
        get() = model.showProgress

    val wocList: LiveData<ArrayList<WeatherOfCity>>
        get() = model.wocList

    val isRefreshing: LiveData<Boolean>
        get() = model.isRefreshing

    fun requestRefresh() {
        model.requestRefresh()
    }
}