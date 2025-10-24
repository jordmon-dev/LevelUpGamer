package com.example.levelup_gamer.viewmodel

sealed class AppScreens(val route: String) {

    object LoginScreen : AppScreens(route = "Login_screen")
    object HomeScreen: AppScreens(route = "home_screen")
    object AboutScreen: AppScreens(route = "about_screen")
    object RegistroScreen: AppScreens(route = "Registro_screen")
    object PerfilScreen: AppScreens(route = "Perfil_screen")
    object CatalogoScreen : AppScreens(route = "Catalogo_screen")
    object CarritoScreend : AppScreens(route = "Carrito_screen")

}