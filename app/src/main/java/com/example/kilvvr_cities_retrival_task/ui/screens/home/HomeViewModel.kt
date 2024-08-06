package com.example.kilvvr_cities_retrival_task.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilvvr_cities_retrival_task.repository.CitiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.measureTime

class HomeViewModel(private val repository: CitiesRepository) : ViewModel() {

//    private var _isLoading = MutableStateFlow(true)
//    val isLoading get() = _isLoading.asStateFlow()

    /**
     * collecting the data from the repository preparing all cities list will be consumed by the ui
     * in case of no query is entered by user.
     * In addition to indexing all the cities to be prepared for prefix searching.
     * estimated time of those operations will be in range of 3 to 4 seconds when the app is operating
     */
    init {
        viewModelScope.launch(Dispatchers.Default) {
            val executionTime = measureTime {
                val allCities = repository.getCitiesFromJson()
                repository.indexCities(allCities)
            }
            Log.i("RepositoryImpl", executionTime.toString())
        }
    }
}