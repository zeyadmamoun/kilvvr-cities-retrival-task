package com.example.kilvvr_cities_retrival_task.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kilvvr_cities_retrival_task.models.City
import com.example.kilvvr_cities_retrival_task.repository.CitiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val repository: CitiesRepository) : ViewModel() {
    /**
     * declaring ui state that holds all the states that effects the home screen.
     * when there is no query in the search field we will show allCities list from the ui state,
     * but when user
     */
    private var _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    /**
     * Represents the current user input in the search bar.
     */
    var userInput by mutableStateOf("")
        private set

    /**
     * collecting the data from the repository preparing all cities list will be consumed by the ui
     * in case of no query is entered by user.
     * In addition to indexing all the cities to be prepared for prefix searching.
     * estimated time of those operations will be in range of 3 to 4 seconds when the app is operating
     */
    init {
        viewModelScope.launch(Dispatchers.Default) {
            val allCities = repository.getCitiesFromJson()
            repository.indexCities(allCities)
            withContext(Dispatchers.Main) {
                _uiState.update {
                    it.copy(
                        allCities = allCities,
                        isLoading = false
                    )
                }
            }
        }
    }
    /**
     * Updates the user input and triggers a search filter update.
     *
     * @param query The new user input string.
     */
    fun updateUserInput(query: String){
        userInput = query
        updateSearchFilter(userInput)
    }
    /**
     * Updates the filtered list of cities based on the given prefix.
     *
     * @param prefix The prefix to filter cities by.
     */
    private fun updateSearchFilter(prefix: String) {
        viewModelScope.launch(Dispatchers.Main) {
            val result = repository.searchForCities(prefix)
            _uiState.update {
                it.copy(
                    filteredCities = result
                )
            }
        }
    }
}

data class UiState(
    val isLoading: Boolean = true,
    val allCities: List<City> = emptyList(),
    val filteredCities: List<City> = emptyList()
)