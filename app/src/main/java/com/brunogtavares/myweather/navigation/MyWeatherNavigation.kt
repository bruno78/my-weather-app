package com.brunogtavares.myweather.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.brunogtavares.myweather.screen.*

@ExperimentalComposeUiApi
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
        composable(
            route = MyWeatherScreens.MainScreen.name + "/{city}",
            arguments = listOf( navArgument(name = "city") { type = NavType.StringType })
        ) { navBack ->
            navBack.arguments?.getString("city").let { city ->
                MainScreen(
                    navController = navController,
                    viewModel = hiltViewModel(),
                    city = city
                )
            }
        }
        composable(MyWeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }
        composable(MyWeatherScreens.FavoritesScreen.name) {
            FavoritesScreen(navController = navController)
        }
        composable(MyWeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }
    }
}
