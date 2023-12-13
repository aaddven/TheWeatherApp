package com.example.theweather.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.theweather.R
import com.example.theweather.data.DataOrException
import com.example.theweather.model.Geocoding.GeocodingApiResponse
import com.example.theweather.model.Geocoding.GeocodingResult
import com.example.theweather.navigation.WeatherScreens
import com.example.theweather.screens.main.showData


@Composable
fun SearchScreen(navController: NavController, searchViewModel: SearchViewModel){

    var searchText by remember { mutableStateOf("") }
    var shouldNavigate by remember { mutableStateOf(false) }
    var latitude by remember { mutableStateOf<Double?>(null) }
    var longitude by remember { mutableStateOf<Double?>(null) }

    LaunchedEffect(latitude, longitude) {
        if (shouldNavigate && latitude != null && longitude != null) {

            val route = "${WeatherScreens.MainScreen.name}/$latitude/$longitude"
            navController.navigate(route)
            shouldNavigate = false
        }
    }

    Surface(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFF00897B), // Teal
                    Color(0xFF26C6DA)  // Light Turquoise
                )
            )
        ),
        shape = RoundedCornerShape(4.dp)    ) {

        Column(
            modifier = Modifier
                .padding(1.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .padding(1.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {


                Card(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(), elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    SearchBar(searchText = searchText,
                        onSearchTextChange = {newText -> searchText = newText},
                        onSearchTriggered = {shouldNavigate = true})

                    if (shouldNavigate) {
                        if (isValidInput(searchText)) {
                            GetCoordinatesGeocoder(
                                searchViewModel = searchViewModel,
                                searchText = searchText
                            ) { lat, lon ->
                                latitude = lat
                                longitude = lon
                            }
                        } else {
                            shouldNavigate = false
                        }
                    }


                }

            }

        }

    }


}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(searchText: String,
              onSearchTextChange: (String) -> Unit,
              onSearchTriggered: () -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var localSearchText by remember { mutableStateOf(searchText) }

    OutlinedTextField(
        value = localSearchText,
        onValueChange = {localSearchText = it},
        label = { Text("Search City") },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchTextChange(localSearchText)
                onSearchTriggered()
                keyboardController?.hide()
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}


@Composable
fun GetCoordinatesGeocoder(
    searchViewModel: SearchViewModel,
    searchText: String,
    onCoordinatesReceived: (Double, Double) -> Unit
) {
    val geocodedData = produceState<DataOrException<GeocodingApiResponse, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = searchViewModel.getCoordinatesForCity(searchText)
    }.value

    if (geocodedData.loading == true) {
        CircularProgressIndicator()
    } else if (geocodedData.data != null) {
        val lat = geocodedData.data!!.results[0].geometry.lat
        val lon = geocodedData.data!!.results[0].geometry.lng
        onCoordinatesReceived(lat, lon)
    }
}

fun isValidInput(input: String): Boolean {
    return input.toIntOrNull() == null
}

