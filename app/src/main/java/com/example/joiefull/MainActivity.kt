package com.example.joiefull

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.joiefull.responsive.WindowInfo
import com.example.joiefull.responsive.rememberWindowInfo
import com.example.joiefull.ui.theme.JoiefullTheme
import com.example.joiefull.userInterface.detail.DetailScreen
import com.example.joiefull.userInterface.home.HomeDisplay
import com.example.joiefull.userInterface.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        setContent {
            JoiefullTheme {



                Surface(color = MaterialTheme.colorScheme.background) {
                    Box(Modifier.safeDrawingPadding()) {
                        val navController = rememberNavController()

                        val windowInfo = rememberWindowInfo()
                        if (windowInfo.screenWidth == WindowInfo.WindowType.Phone) {


                            NavHost(
                                navController = navController,
                                startDestination = NavigationItem.Home.route
                            ) {

                                composable(NavigationItem.Home.route) {
                                    HomeDisplay(
                                        navController = navController,


                                        )
                                }

                                composable(
                                    NavigationItem.Detail.route,
                                    arguments = listOf(navArgument("clothesId") {

                                        type = NavType.IntType
                                    })
                                ) {
                                    val clothesId = it.arguments?.getInt("clothesId")
                                    if (clothesId != null) {
                                        DetailScreen(clothesId, navController = navController)
                                    }


                                }
                            }


                        } else {


                            HomeDisplay(
                                navController = navController,

                                )


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
    object Detail : NavigationItem("${Screen.DETAIL.name}/{clothesId}")
}





