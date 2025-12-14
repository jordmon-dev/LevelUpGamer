package com.example.levelup_gamer.navegate

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// --- BLOQUE DE IMPORTS DE PANTALLAS (Fundamental) ---
import com.example.levelup_gamer.screen.*
// --- IMPORTS DE VIEWMODELS ---
import com.example.levelup_gamer.viewmodel.UsuarioViewModel
import com.example.levelup_gamer.viewmodel.ProductoViewModel
import com.example.levelup_gamer.viewmodel.CarritoViewModel
import com.example.levelup_gamer.viewmodel.OfertasViewModel

@Composable
fun AppNavigate() {
    val navController = rememberNavController()

    // Instancias de ViewModels
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val productoViewModel: ProductoViewModel = viewModel()
    val carritoViewModel: CarritoViewModel = viewModel()
    val ofertasViewModel: OfertasViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // --- AUTENTICACIÓN ---
        composable("login") {
            LoginScreen(navController, usuarioViewModel)
        }
        composable("registro") {
            RegistroScreen(navController, usuarioViewModel)
        }

        // --- PANTALLAS PRINCIPALES ---
        composable("home") {
            HomeScreen(
                navController = navController,
                productoViewModel = productoViewModel,
                carritoViewModel = carritoViewModel,
                viewModel = usuarioViewModel
            )
        }
        composable("catalogo") {
            CatalogoScreen(
                navController = navController,
                productoViewModel = productoViewModel,
                carritoViewModel = carritoViewModel
            )
        }
        composable("carrito") {
            CarritoScreen(
                navController = navController,
                carritoViewModel = carritoViewModel,
                productoViewModel = productoViewModel
            )
        }
        composable("perfil") {
            PerfilScreen(navController, usuarioViewModel)
        }

        // --- PANTALLAS SECUNDARIAS ---
        composable("about") {
            AboutScreen(navController)
        }
        composable("ayuda") {
            AyudaScreen(navController)
        }
        composable("ofertas") {
            OfertasScreen(navController, ofertasViewModel)
        }
        composable("pago") {
            PagoScreen(navController, carritoViewModel, usuarioViewModel)
        }
    }
}