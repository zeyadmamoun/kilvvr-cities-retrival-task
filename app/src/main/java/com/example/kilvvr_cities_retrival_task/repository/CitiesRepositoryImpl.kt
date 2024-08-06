package com.example.kilvvr_cities_retrival_task.repository

import android.content.Context
import android.util.Log
import com.example.kilvvr_cities_retrival_task.models.City

/**
 * Cities repository is the main data source for the project
 * containing two methods:
 *
 * 1- getCitiesFromJson() --> responsible for reading the data from json file,
 * serializing it to listOf City object
 *
 * 2- indexCities() --> responsible for indexing the list we got from the first method,
 * achieving fast data retrieval
 */

class CitiesRepositoryImpl(private val context: Context) : CitiesRepository {

    override fun getCitiesFromJson(): List<City> {
        try {
            val data = context.assets.open("cities.json").bufferedReader().use {
                it.readText()
            }
            Log.i("RepositoryImpl",data)
            TODO("Not yet implemented")
        }catch (e: Exception){
            Log.i("RepositoryImpl",e.toString())
        }
        return emptyList()
    }

    override fun indexCities(cities: List<City>) {
        TODO("Not yet implemented")
    }
}