package com.example.kilvvr_cities_retrival_task.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.kilvvr_cities_retrival_task.repository.CitiesRepository

class HomeViewModel(private val repository: CitiesRepository) : ViewModel() {

    init {
        repository.getCitiesFromJson()
    }

}