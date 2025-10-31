package com.example.levelup_gamer.viewmodel


sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("login")
    object HomeScreen : AppScreens("home")
    object AboutScreen : AppScreens("about")
    object RegistroScreen : AppScreens("registro")
    object PerfilScreen : AppScreens("perfil")
    object CatalogoScreen : AppScreens("catalogo")
    object CarritoScreen : AppScreens("carrito") // Corregido el nombre
}