package com.kkc.idushomework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kkc.idushomework.R
import com.kkc.idushomework.databinding.WeatherListItemBinding
import com.kkc.idushomework.model.WeatherOfCity

class WeatherListAdapter : RecyclerView.Adapter<WeatherListViewHolder>() {
    private val weathers by lazy { ArrayList<WeatherOfCity>()}
    private val HEADER = 0
    private val ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListViewHolder {
        return if (viewType == HEADER) {
            WeatherListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.weather_list_header, parent, false))
        } else {
            val binding = DataBindingUtil.inflate<WeatherListItemBinding>(LayoutInflater.from(parent.context), R.layout.weather_list_item, parent, false)
            WeatherListViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return weathers.size + 1
    }

    override fun onBindViewHolder(holder: WeatherListViewHolder, position: Int) {
        holder.getBinding()?.let { it.weather = weathers[position] }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADER else ITEM
    }

    fun setWeatherData(list: ArrayList<WeatherOfCity>) {
        weathers.clear()
        weathers.addAll(list)

        notifyDataSetChanged()
    }
}