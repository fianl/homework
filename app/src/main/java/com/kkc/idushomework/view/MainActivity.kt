package com.kkc.idushomework.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.kkc.idushomework.R
import com.kkc.idushomework.databinding.ActivityMainBinding
import com.kkc.idushomework.model.WeatherListModel
import com.kkc.idushomework.viewmodel.WeatherListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy { WeatherListViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.weather = viewModel
    }

    private fun 
}
