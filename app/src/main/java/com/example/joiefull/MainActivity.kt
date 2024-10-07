package com.example.joiefull

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.joiefull.ui.theme.JoiefullTheme
import com.example.joiefull.userInterface.detail.DetailScreen
import com.example.joiefull.userInterface.home.HomeDisplay
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            JoiefullTheme {


                Surface(color = MaterialTheme.colorScheme.background) {
                    Box(Modifier.safeDrawingPadding()) {
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = NavigationItem.Home.route
                        ) {
                            composable(NavigationItem.Home.route) {
                                HomeDisplay(navController = navController)
                            }

                            composable(
                                "detail/{clothesId}",
                                arguments = listOf(navArgument("clothesId") {

                                    type = NavType.IntType
                                })
                            ) {
                                val clothesId = it.arguments?.getInt("clothesId")
                                if (clothesId != null) {
                                    DetailScreen(clothesId)
                                }
                            }
                            composable(route = "home")

                            {  }



                        }
                    }
                }
            }
        }
    }
}

enum class Screen {
    HOME,
    DETAIL,
}

sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
    object Detail : NavigationItem(Screen.DETAIL.name)
}




