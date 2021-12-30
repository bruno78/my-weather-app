package com.brunogtavares.myweather.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.brunogtavares.myweather.screen.MainScreen
import com.brunogtavares.myweather.screen.SplashScreen

@Composable
fun MyWeatherNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MyWeatherScreens.SplashScreen.name
    ) {
        composable(MyWeatherScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        composable(MyWeatherScreens.MainScreen.name) {
            MainScreen(navController = navController)
        }
    }
}