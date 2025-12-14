package com.example.levelup_gamer.navegate

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Importamos todas tus pantallas
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
import com.example.levelup_gamer.ui.theme.screen.CatalogoScreen
// Asegúrate de tener estas pantallas creadas o comenta las líneas si no las usas
// import com.example.levelup_gamer.ui.theme.Screen.NotificacionScreen

// Importamos tus ViewModels
import com.example.levelup_gamer.viewmodel.UsuarioViewModel
import com.example.levelup_gamer.viewmodel.ProductoViewModel
import com.example.levelup_gamer.viewmodel.CarritoViewModel

@Composable
fun AppNavigate() {
    val navController = rememberNavController()

    // ----------------------------------------------------------------------
    // 1. INSTANCIAS GLOBALES DE VIEWMODELS
    // Se crean aquí para sobrevivir a la navegación y compartirse entre pantallas.
    // ----------------------------------------------------------------------

    // ViewModel de Usuario (Login/Registro/Perfil)
    val usuarioViewModel: UsuarioViewModel = viewModel()

    // ViewModel de Productos (Catálogo)
    val productoViewModel: ProductoViewModel = viewModel()

    // ViewModel de Carrito (Compras)
    val carritoViewModel: CarritoViewModel = viewModel()


    // ----------------------------------------------------------------------
    // 2. DEFINICIÓN DE RUTAS (NavHost)
    // ----------------------------------------------------------------------
    NavHost(
        navController = navController,
        startDestination = "login" // La primera pantalla que se ve
    ) {

        // --- AUTENTICACIÓN ---

        composable("login") {
            LoginScreen(
                navController = navController,
                viewModel = usuarioViewModel
            )
        }

        composable("registro") {
            RegistroScreen(
                navController = navController,
                usuarioViewModel = usuarioViewModel // Asegúrate que en RegistroScreen el parámetro se llame así
            )
        }

        // --- NAVEGACIÓN PRINCIPAL ---

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
                productoViewModel = productoViewModel // Por si necesitas ver productos desde el carrito
            )
        }

        composable("perfil") {
            PerfilScreen(
                navController = navController,
                viewModel = usuarioViewModel
            )
        }

        // --- OTRAS PANTALLAS (Asegúrate de que existan o coméntalas) ---

        composable("about") {
            AboutScreen(navController = navController)
        }

        composable("ayuda") {
            AyudaScreen(navController = navController)
        }

        composable("ofertas") {
            OfertasScreen(navController = navController)
        }

        composable("pago") {
            PagoScreen(navController = navController)
        }
    }
}