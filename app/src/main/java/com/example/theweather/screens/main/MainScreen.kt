package com.example.theweather.screens.main


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.theweather.data.DataOrException
import com.example.theweather.model.Geocoding.GeocodingApiResponse
import com.example.theweather.model.Weather.WeatherApiResponse
import com.example.theweather.model.Weather.WeatherItem
import com.example.theweather.utils.formatDecimals
import com.example.theweather.widgets.HumidityWindRow
import com.example.theweather.widgets.SunRiseSunSetRow
import com.example.theweather.widgets.WeatherDetailRow
import com.example.theweather.widgets.WeatherStateImage


@Composable
fun MainScreen(navController: NavController,mainViewModel: MainViewModel = hiltViewModel()
               , latitude : Double , longitude : Double){

    showData(mainViewModel,latitude,longitude)

}





@Composable
fun showData(mainViewModel: MainViewModel,latitude: Double,
             longitude: Double){


    
    val weatherData = produceState<DataOrException<WeatherApiResponse,Boolean,Exception>>(
        initialValue = DataOrException(loading = true)){

            value = mainViewModel.getWeatherData(latitude,longitude)
    }.value

    if(weatherData.loading ==  true){
        CircularProgressIndicator()
    }else if(weatherData.data != null){
        showData( data = weatherData.data!!)
    }
}





@Composable
fun showData(data: WeatherApiResponse) {

    val imageUrl = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"

    Surface(modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(4.dp)    ) {

        Column(modifier = Modifier
            .padding(1.dp)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Box(modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth()){
                

                Card(modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(), elevation = CardDefaults.cardElevation(8.dp)) {

                    Column(
                        modifier = Modifier.padding(2.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        Row(modifier = Modifier
                            .padding(2.dp)
                            .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween){

                            Text(text = "${data.city.name}",
                                style = MaterialTheme.typography.labelLarge,
                                color = DarkGray,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier.padding(6.dp))

                            Text(text = "${data.list[0].dt_txt.subSequence(11,16)}",
                                style = MaterialTheme.typography.labelLarge,
                                color = Gray,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Light,
                                modifier = Modifier.padding(6.dp))

                        }



                        Divider()

                        Surface(modifier = Modifier
                            .padding(8.dp)
                            .size(230.dp),
                            shape = CircleShape,
                            color = Color(0xFFFFC400)) {

                            Column(verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally) {

                                WeatherStateImage(imageUrl = imageUrl)

                                Text(text = formatDecimals(data.list[0].main.temp)+"ºC",
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.ExtraBold)

                                Text(text = "${data.list[0].weather[0].main}",
                                    fontSize = 16.sp,
                                    fontStyle = FontStyle.Italic)

                                Text(text = "(${data.list[0].weather[0].description})",
                                    fontSize = 16.sp,
                                    color = DarkGray,
                                    fontStyle = FontStyle.Normal)

                            }

                        }


                        HumidityWindRow(data = data.list[0])

                        SunRiseSunSetRow(data = data.city)

                    }
                }
            }

            Divider()

            Text(text = "Forecast",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold, color = Gray)

            Divider()
            
            
            Surface(modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
                    color = Color(0xFFEEF1EF),
                    shape = RoundedCornerShape(15.dp)
            ) {
                
                LazyColumn(modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(1.dp)){
                    items(items = data.list){item: WeatherItem ->
                         WeatherDetailRow(data = item)
                    }
                }
                
            }
        }
    }

}


