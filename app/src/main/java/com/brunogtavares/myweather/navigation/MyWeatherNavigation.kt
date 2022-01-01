package com.brunogtavares.myweather.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.brunogtavares.myweather.screen.MainScreen
import com.brunogtavares.myweather.screen.SearchScreen
import com.brunogtavares.myweather.screen.SplashScreen

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
        composable(MyWeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }
    }
}
