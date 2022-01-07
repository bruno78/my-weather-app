package com.brunogtavares.myweather.screen

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.brunogtavares.myweather.widgets.AppBar

@Composable
fun FavoritesScreen(navController: NavController) {
    Scaffold(
        topBar = {
            AppBar(
                title = "Favorites",
                navController = navController,
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,
                elevation = 0.dp
            ) {
                navController.popBackStack()
            }
        }
    ) {
        Text("FavoritesScreen")
    }
}