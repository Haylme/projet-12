package com.example.joiefull.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.joiefull.userInterface.detail.DetailScreen
import com.example.joiefull.userInterface.home.HomeDisplay
/**
@Composable
fun SetupNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable (
            route = Screen.Home.route

        ) {
            HomeDisplay(navController = navController)

        }
        composable (
            route = Screen.Detail.route

        ) {
            DetailScreen()

        }


    }

}**/