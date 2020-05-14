package com.kkc.idushomework.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kkc.idushomework.R
import com.kkc.idushomework.adapter.WeatherListAdapter
import com.kkc.idushomework.databinding.ActivityMainBinding
import com.kkc.idushomework.model.WeatherListModel
import com.kkc.idushomework.viewmodel.WeatherListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy { WeatherListViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.weather = viewModel

        weatherListInit()
        weatherRefreshSet()
    }

    private fun weatherListInit() {
        binding.weatherList.adapter = WeatherListAdapter()
    }

    private fun weatherRefreshSet() {
        binding.weatherRefresh.setOnRefreshListener(this)
        val observer = Observer<Boolean> { it ->
            if(!it && binding.weatherRefresh.isRefreshing)
                binding.weatherRefresh.isRefreshing = it
        }
        viewModel.isRefreshing.observe(this, observer)
    }

    override fun onRefresh() {
        viewModel.requestRefresh()
    }
}
