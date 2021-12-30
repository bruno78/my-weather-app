package com.brunogtavares.myweather.screen

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.brunogtavares.myweather.viewmodel.MainScreenState
import com.brunogtavares.myweather.viewmodel.MainViewModel

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel
){
    viewModel.onLoadWeather("New York")

    when(viewModel.screenState.value) {
        is MainScreenState.Loading -> CircularProgressIndicator()
        is MainScreenState.LoadSuccess -> Text("Main Screen")
        is MainScreenState.LoadFailed -> Text("Failed")
    }

}