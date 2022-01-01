package com.brunogtavares.myweather.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.brunogtavares.myweather.model.WeatherLocation
import com.brunogtavares.myweather.ui.theme.MyWeatherYellow
import com.brunogtavares.myweather.utils.formatDate
import com.brunogtavares.myweather.utils.formatDecimals
import com.brunogtavares.myweather.viewmodel.MainScreenState
import com.brunogtavares.myweather.viewmodel.MainViewModel
import com.brunogtavares.myweather.widgets.*

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
            color = MyWeatherYellow
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherImage(todayWeather.weather.first().icon)
                Text(
                    text = formatDecimals(todayWeather.temp.day) + "°",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = todayWeather.weather.first().description,
                    fontStyle = FontStyle.Italic
                )
            }
        }
        HumidityWindPressureRow(todayWeather)
        Divider()
        SunriseSunsetRows(todayWeather)
        DailyWeather(dailyWeather = weatherLocation.list)
    }
}