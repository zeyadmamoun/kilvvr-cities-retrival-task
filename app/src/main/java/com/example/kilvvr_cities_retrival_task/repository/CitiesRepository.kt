package com.example.kilvvr_cities_retrival_task.repository

import com.example.kilvvr_cities_retrival_task.models.City

interface CitiesRepository {
    fun getCitiesFromJson(): List<City>
    fun indexCities(cities:List<City>)
}