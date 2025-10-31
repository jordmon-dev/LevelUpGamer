package com.example.levelup_gamer.navegate

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.levelup_gamer.LoginScreen
import com.example.levelup_gamer.ui.theme.Screen.BienvenidaScreen
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
            LoginScreen(usuarioViewModel,navController)
        }
        composable(route="bienvenida") {
            BienvenidaScreen(usuarioViewModel)
        }
    }

}

