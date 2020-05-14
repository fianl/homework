package com.kkc.idushomework.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kkc.idushomework.model.WeatherOfCity

@BindingAdapter("app:weatherRepository")
fun setWeatherData(view: RecyclerView, list: ArrayList<WeatherOfCity>?) {
    list?.let {wheatherList ->
        view.adapter?.let {
            if (it is WeatherListAdapter) {
                it.setWeatherData(wheatherList)
            }
        }?: run {
            val adapter = WeatherListAdapter()
            view.adapter = adapter
            adapter.setWeatherData(wheatherList)
        }
    }
}

@BindingAdapter("app:temperature")
fun setTemperature(view: TextView, temp: Float) {
    view.text = "${temp.toInt()}"
}

@BindingAdapter("app:glideImg")
fun loadImageByGlide(view: ImageView, abbr: String) {
    val url = "https://www.metaweather.com/static/img/weather/png/${abbr}.png"
    Glide.with(view)
        .load(url)
        .into(view)
}

@BindingAdapter("app:visibility")
fun setViewVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}