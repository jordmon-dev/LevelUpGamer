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
import com.example.levelup_gamer.viewmodel.OfertasViewModel // ⬅️ ¡IMPORTANTE!

@Composable
fun AppNavigate() {
    val navController = rememberNavController()

    // --- INSTANCIAS DE VIEWMODELS ---
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val productoViewModel: ProductoViewModel = viewModel()
    val carritoViewModel: CarritoViewModel = viewModel()

    // ⬅️ AGREGAMOS ESTO:
    val ofertasViewModel: OfertasViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // --- AUTH ---
        composable("login") {
            LoginScreen(navController, usuarioViewModel)
        }

        composable("registro") {
            RegistroScreen(navController, usuarioViewModel)
        }

        // --- HOME & CATÁLOGO ---
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

        // 🔴 AQUÍ ESTABA EL ERROR: Ahora pasamos el viewModel
        composable("ofertas") {
            OfertasScreen(
                navController = navController,
                viewModel = ofertasViewModel // ⬅️ CORREGIDO
            )
        }

        composable("pago") {
            // Asegúrate que PagoScreen esté bien definido. Si pide viewModel, agrégalo aquí también.
            PagoScreen(navController)
        }
    }
}