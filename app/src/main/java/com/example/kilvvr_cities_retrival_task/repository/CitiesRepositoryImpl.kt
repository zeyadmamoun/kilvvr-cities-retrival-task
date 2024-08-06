package com.example.kilvvr_cities_retrival_task.repository

import android.content.Context
import android.util.Log
import com.example.kilvvr_cities_retrival_task.models.City
import com.example.kilvvr_cities_retrival_task.utils.Trie
import kotlinx.serialization.json.Json

/**
 * Cities repository is the main data source for the project
 * containing two methods:
 *
 * 1- getCitiesFromJson() --> responsible for reading the data from json file,
 *    serializing it to listOf City object
 *
 * 2- indexCities() --> responsible for indexing the list we got from the first method,
 *    achieving fast data retrieval
 *
 * 3- searchForCities --> uses search method of the Trie to return list of cities resulted by the prefix,
 *    and sorting it in alphabetical order due to city name
 */

class CitiesRepositoryImpl(private val context: Context) : CitiesRepository {

    private val indexedCities = Trie()
    /**
     * getCitiesFromJson() -> return list of City Object after
     * reading data from json file as string,
     * then try to decode the jsonString List of City
     */
    override fun getCitiesFromJson(): List<City> {
        return try {
            val data = context.assets.open("cities.json").bufferedReader()
                .use {
                    it.readText()
                }
            val cities = Json.decodeFromString<List<City>>(data)
            cities
        } catch (e: Exception) {
            Log.e("RepositoryImpl", e.toString())
            emptyList()
        }
    }
    /**
     * calling this function from initialization of the HomeViewModel()
     */
    override fun indexCities(cities: List<City>) {
        cities.forEach { city -> indexedCities.insert(city) }
    }
    /**
     * calling this function everytime user enter character in the search field
     */
    override fun searchForCities(prefix: String): List<City>{
        return indexedCities.search(prefix).sortedWith(compareBy({it.name}, { it.country}))
    }
}