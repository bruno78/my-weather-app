package com.brunogtavares.myweather.screen

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.brunogtavares.myweather.model.WeatherLocation
import com.brunogtavares.myweather.viewmodel.MainScreenState
import com.brunogtavares.myweather.viewmodel.MainViewModel
import com.brunogtavares.myweather.widgets.AppBar

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    viewModel.onLoadWeather("New York")

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
                title = weatherLocation.city.name + " ,${weatherLocation.city.country}",
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
    Text(weatherLocation.city.name)
}