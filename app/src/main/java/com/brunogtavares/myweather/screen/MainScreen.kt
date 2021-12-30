package com.brunogtavares.myweather.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.brunogtavares.myweather.model.WeatherLocation
import com.brunogtavares.myweather.utils.Constants.WEATHER_IMAGE_BASE_URL
import com.brunogtavares.myweather.utils.formatDate
import com.brunogtavares.myweather.utils.formatDecimals
import com.brunogtavares.myweather.viewmodel.MainScreenState
import com.brunogtavares.myweather.viewmodel.MainViewModel
import com.brunogtavares.myweather.widgets.AppBar

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    viewModel.onLoadWeather("Moscow")

    when (viewModel.screenState.value) {
        is MainScreenState.Loading -> CircularProgressIndicator()
        is MainScreenState.LoadSuccess -> MainScaffold(
            weatherLocation = (viewModel.screenState.value as MainScreenState.LoadSuccess).data,
            navController = navController
        )
        is MainScreenState.LoadFailed -> Text("Failed")
    }


}

@Composable
fun MainScaffold(
    weatherLocation: WeatherLocation,
    navController: NavController
) {
    Scaffold(
        topBar = {
            AppBar(
                title = weatherLocation.city.name + ", ${weatherLocation.city.country}",
                navController = navController,
                elevation = 5.dp
            )
        }
    ) {
        MainContent(weatherLocation)
    }
}

@Composable
fun MainContent(weatherLocation: WeatherLocation) {
    val todayWeather = weatherLocation.list.first()
    val imageUrl = WEATHER_IMAGE_BASE_URL + "${todayWeather.weather.first().icon}.png"
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(todayWeather.dt),
            modifier = Modifier.padding(6.dp),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
        )
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Image
                WeatherImage(imageUrl)
                Text(
                    text = formatDecimals(todayWeather.temp.day) +"°",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = todayWeather.weather.first().description,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@Composable
fun WeatherImage(imageUrl: String) {
    Image(
        painter = rememberImagePainter(imageUrl),
        contentDescription = null, // not important for accessibility
        modifier = Modifier.size(80.dp)
    )
}