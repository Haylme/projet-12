package com.example.joiefull

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.joiefull.ui.theme.JoiefullTheme
import com.example.joiefull.userInterface.DetailFragment
import com.example.joiefull.userInterface.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            JoiefullTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                   MyApp()

                    MyFragmentContainer(fragmentManager = supportFragmentManager)

                }


            }
        }
    }

}

@Composable
fun MyFragmentContainer(fragmentManager: FragmentManager) {
    AndroidView(
        factory = { context->
            FragmentContainerView(context).apply {
                // Add your Fragment here
                val fragment = HomeFragment()
                fragmentManager.beginTransaction()
                    .add(id, fragment)
                    .commit()
            }
        },
      //  update = { fragmentContainerView ->
            // Update the Fragment if needed
       // }
    )
}


@Composable
fun MyApp() {
    val navController = rememberNavController()
    Surface(color = MaterialTheme.colorScheme.primary) {
        NavHost(navController = navController, startDestination = AppNav.Homefragment.name) {
            composable(AppNav.Homefragment.name) {
                AndroidView(factory = { context ->
                    FragmentContainerView(context).apply {
                        id = android.R.id.content
                        val fragment = HomeFragment()
                        (context as ComponentActivity).supportFragmentManager.beginTransaction()
                            .replace(id, fragment)
                            .commit()
                    }
                })
            }
            composable(AppNav.Detailfragment.name) {
                AndroidView(factory = { context ->
                    FragmentContainerView(context).apply {
                        id = android.R.id.content
                        val fragment = DetailFragment()
                        (context as ComponentActivity).supportFragmentManager.beginTransaction()
                            .replace(id, fragment)
                            .commit()
                    }
                })
            }
        }
    }
}

enum class AppNav{

    Homefragment,
    Detailfragment


}