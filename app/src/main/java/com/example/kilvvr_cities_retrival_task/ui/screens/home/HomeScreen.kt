package com.example.kilvvr_cities_retrival_task.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.kilvvr_cities_retrival_task.models.City
import com.example.kilvvr_cities_retrival_task.models.Coordinates
import com.example.kilvvr_cities_retrival_task.ui.screens.Screen
import com.example.kilvvr_cities_retrival_task.ui.theme.KilvvrcitiesretrivaltaskTheme
import org.koin.androidx.compose.koinViewModel

/**
 * Composable function that represents the main home screen of the application.
 *
 * @param viewModel The ViewModel that manages the UI state and logic for this screen.
 */
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navController: NavHostController
) {
    val uiState = viewModel.uiState.collectAsState()

    if (uiState.value.isLoading) {
        IndeterminateCircularIndicator()
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            SearchBar(
                userQuery = viewModel.userInput,
                onValueChanged = { viewModel.updateUserInput(it) },
                onClearClicked = { viewModel.updateUserInput("") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .heightIn(56.dp)
            )
            LazyColumn {
                if (viewModel.userInput == "") {
                    items(uiState.value.allCities) {city ->
                        CityCard(
                            city = city,
                            onCardClicked = {
                                navController.navigate(
                                    Screen.Map.createRoute(city.coord.lat,city.coord.lon)
                                )
                            }
                        )
                    }
                } else {
                    items(uiState.value.filteredCities) { city ->
                        CityCard(
                            city = city,
                            onCardClicked = {
                                navController.navigate(
                                    Screen.Map.createRoute(city.coord.lat,city.coord.lon)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Composable function that creates a search bar for user input.
 *
 * @param userQuery The current text in the search bar.
 * @param onValueChanged Callback function invoked when the text in the search bar changes.
 * @param onClearClicked Callback function invoked when the clear button is clicked.
 * @param modifier Modifier for customizing the layout of the search bar.
 */
@Composable
fun SearchBar(
    userQuery: String,
    onValueChanged: (String) -> Unit,
    onClearClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    OutlinedTextField(
        value = userQuery,
        onValueChange = onValueChanged,
        interactionSource = interactionSource,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "search icon",
            )
        },
        trailingIcon = if (isFocused) {
            {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "clear Text",
                    modifier = Modifier.clickable {
                        onClearClicked()
                    }
                )
            }
        } else null,
        shape = RoundedCornerShape(30.dp),
        modifier = modifier
    )
}

/**
 * Composable function that creates a card to display city information.
 *
 * @param city The City object containing information to be displayed.
 * @param modifier Modifier for customizing the layout of the card.
 */
@Composable
fun CityCard(
    city: City,
    onCardClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCardClicked() }
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 12.dp
                )
        ) {
            Text(
                text = "${city.name},${city.country}",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            Text(
                text = city.coord.print(),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

/**
 * Composable function that creates an indeterminate circular progress indicator.
 */
@Composable
fun IndeterminateCircularIndicator() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.width(64.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp)
                .height(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityCardPreview() {
    val coordinates = Coordinates(lon = 33.900002, lat = 44.599998)
    val city = City(name = "Holubynka", coord = coordinates, id = 708546, country = "UA")
    KilvvrcitiesretrivaltaskTheme {
        CityCard(
            city = city,
            onCardClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IndeterminateCircularIndicatorPreview() {
    IndeterminateCircularIndicator()
}