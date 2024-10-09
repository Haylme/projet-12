package com.example.joiefull.navigation




 sealed class Screen (val route: String){

     object Home : Screen(AllScreens.Home.name)
     object Detail : Screen(AllScreens.Detail.name)


}

enum class AllScreens {

    Home,
    Detail
}