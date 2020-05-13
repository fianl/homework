package com.kkc.idushomework.model

import com.kkc.idushomework.data.entity.Weather

data class WeatherOfCity(var title: String? = null, val today: Weather, val tomorrow: Weather)