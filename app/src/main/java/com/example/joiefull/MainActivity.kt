package com.example.joiefull

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.joiefull.ui.theme.JoiefullTheme

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
                    HomeDisplay()

                }
            }
        }
    }
}

/**
@Composable
fun MyApp(starDestination: String = NavigationItem.Home.route) {
    val navController = rememberNavController()
    Surface(color = MaterialTheme.colorScheme.primary) {
        NavHost(navController = navController, startDestination = starDestination) {
            composable(NavigationItem.Home.route) {
                HomeFragment.newInstance()
            }
            composable(NavigationItem.Detail.route) {
                DetailFragment.newInstance()
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
}**/
