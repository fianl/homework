package com.kkc.idushomework.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kkc.idushomework.model.WeatherOfCity

@BindingAdapter("app:weatherRepository")
fun setWeatherData(view: RecyclerView, list: ArrayList<WeatherOfCity>) {
    view.adapter?.let {
        if (it is WeatherListAdapter) {
            it.setWeatherData(list)
        }
    }?: run {
        val adapter = WeatherListAdapter()
        view.adapter = adapter
        adapter.setWeatherData(list)
    }
}

@BindingAdapter("app:glideImg")
fun loadImageByGlide(view: ImageView, url: String) {

}

@BindingAdapter("app:visibility")
fun setViewVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}