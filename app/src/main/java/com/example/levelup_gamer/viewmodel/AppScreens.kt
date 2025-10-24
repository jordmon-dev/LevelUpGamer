package com.example.levelup_gamer.viewmodel

sealed class AppScreens(val route: String) {

    object LoginScreen : AppScreens(route = "Login_screen")
    object HomeScreen: AppScreens(route = "home_screen")
    object AboutScreen: AppScreens(route = "about_screen")
}