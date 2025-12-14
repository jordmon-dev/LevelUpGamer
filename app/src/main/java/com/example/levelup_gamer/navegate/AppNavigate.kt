package com.example.levelup_gamer.navegate

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// --- PANTALLAS ---
import com.example.levelup_gamer.ui.theme.Screen.LoginScreen
import com.example.levelup_gamer.ui.theme.Screen.RegistroScreen
import com.example.levelup_gamer.ui.theme.Screen.HomeScreen
import com.example.levelup_gamer.ui.theme.Screen.CatalogoScreen
import com.example.levelup_gamer.ui.theme.Screen.CarritoScreen
import com.example.levelup_gamer.ui.theme.Screen.PerfilScreen
import com.example.levelup_gamer.ui.theme.Screen.AboutScreen
import com.example.levelup_gamer.ui.theme.Screen.AyudaScreen
import com.example.levelup_gamer.ui.theme.Screen.OfertasScreen
import com.example.levelup_gamer.ui.theme.Screen.PagoScreen

// --- VIEWMODELS ---
import com.example.levelup_gamer.viewmodel.UsuarioViewModel
import com.example.levelup_gamer.viewmodel.ProductoViewModel
import com.example.levelup_gamer.viewmodel.CarritoViewModel
import com.example.levelup_gamer.viewmodel.OfertasViewModel

@Composable
fun AppNavigate() {
    val navController = rememberNavController()

    // 1. INSTANCIAS COMPARTIDAS DE VIEWMODELS
    // Se crean aquí para que sobrevivan al cambio de pantalla
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

        // --- PRINCIPAL ---
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

        // --- SECUNDARIAS ---
        composable("about") {
            AboutScreen(navController)
        }

        composable("ayuda") {
            AyudaScreen(navController)
        }

        composable("ofertas") {
            OfertasScreen(
                navController = navController,
                viewModel = ofertasViewModel
            )
        }

        // 🔴 AQUÍ ESTABA EL ERROR: Ahora pasamos los ViewModels necesarios
        composable("pago") {
            PagoScreen(
                navController = navController,
                carritoViewModel = carritoViewModel,
                usuarioViewModel = usuarioViewModel
            )
        }
    }
}