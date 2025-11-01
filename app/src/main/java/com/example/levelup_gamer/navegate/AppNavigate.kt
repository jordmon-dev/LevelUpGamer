package com.example.levelup_gamer.navegate

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.levelup_gamer.ui.theme.Screen.BienvenidaScreen
import com.example.levelup_gamer.ui.theme.Screen.LoginScreen
import com.example.levelup_gamer.viewmodel.UsuarioViewModel


@Composable
fun AppNavigate(){
    val navController = rememberNavController()
    val usuarioViewModel: UsuarioViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login")
    {
        composable(route = "login") {
            LoginScreen(navController,usuarioViewModel)
        }
        composable(route="bienvenida") {
            BienvenidaScreen(usuarioViewModel)
        }
        composable(route = "bienvenida") {
            // ... Llama a BienvenidaScreen
        }
        composable(route = "registro") {
            // ... Llama a RegistroScreen
        }
        composable(route = "home") {
            // ... Llama a la función Home(navController) que acabas de corregir
        }
        composable(route = "catalogo") {
            // ... Llama a CatalogoScreen(navController)
        }
        composable(route = "perfil") {
            // ... Llama a PerfilScreen(navController)
        }
        composable(route = "carrito") {
            // ... Llama a CarritoScreen(navController)
        }
        composable(route = "about") {
            // ... Llama a AboutScreen(navController)
        }

    }

}



