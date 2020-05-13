package com.kkc.idushomework.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kkc.idushomework.databinding.WeatherListItemBinding

class WeatherListViewHolder: RecyclerView.ViewHolder {
    private var binding: WeatherListItemBinding? = null

    constructor(view: View): super(view)
    constructor(binding: WeatherListItemBinding): super(binding.root) {
        this.binding = binding
    }

    fun getBinding() = binding
}