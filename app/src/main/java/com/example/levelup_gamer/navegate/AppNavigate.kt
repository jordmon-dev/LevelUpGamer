package com.example.levelup_gamer.navegate

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.levelup_gamer.datastore.UserPreferences
import com.example.levelup_gamer.ui.theme.Screen.*
import com.example.levelup_gamer.viewmodel.*

@Composable
fun AppNavigate() {

    val navController = rememberNavController()

    // ViewModels globales
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val productoViewModel: ProductoViewModel = viewModel()
    val carritoViewModel: CarritoViewModel = viewModel()
    val aboutViewModel: AboutViewModel = viewModel()
    val reclamoViewModel: ReclamoViewModel = viewModel()
    val ofertasVM: OfertasViewModel = viewModel()

    // DataStore
    val contexto = LocalContext.current
    val prefs = UserPreferences(contexto)
    val isLogged by prefs.isLogged.collectAsState(initial = false)

    // Pantalla inicial
    val startDestination = if (isLogged) "home" else "login"

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        // LOGIN
        composable("login") {
            LoginScreen(navController, usuarioViewModel)
        }

        // REGISTRO
        composable("registro") {
            RegistroScreen(navController, usuarioViewModel)
        }

        // HOME
        composable("home") {
            HomeScreen(navController, usuarioViewModel)
        }

        // BIENVENIDA
        composable("bienvenida") {
            BienvenidaScreen(usuarioViewModel)
        }

        // CATÁLOGO
        composable("catalogo") {
            CatalogoScreen(
                navController = navController,
                viewModel = productoViewModel,
                carritoViewModel = carritoViewModel
            )
        }

        // OFERTAS
        composable("ofertas") {
            OfertasScreen(navController, ofertasVM)
        }

        // PERFIL
        composable("perfil") {
            PerfilScreen(navController, usuarioViewModel)
        }

        // CARRITO
        composable("carrito") {
            CarritoScreen(navController, carritoViewModel)
        }

        // ABOUT
        composable("about") {
            AboutScreen(navController, aboutViewModel)
        }

        // PAGO
        composable("pago") {
            PagoScreen(navController, carritoViewModel)
        }

        // CONFIRMACIÓN DE PAGO
        composable("confirmacion") {
            ConfirmacionScreen(navController)
        }

        // AYUDA
        composable("ayuda") {
            AyudaScreen(navController)
        }

        // NOTIFICACIONES
        composable("notificaciones") {
            NotificacionScreen(navController)
        }

        // 📸 CÁMARA — AHORA SÍ FUNCIONA
        composable("camaraCaptura") {
            CamaraCapturaScreen(navController, reclamoViewModel)
        }

        // 📍 GPS — AHORA SÍ FUNCIONA
        composable("gps") {
            PantallaGpsScreen(navController, reclamoViewModel)
        }

        // 📄 RECLAMO
        composable("reporteReclamo") {
            ReporteReclamoScreen(navController, reclamoViewModel)
        }

        // ✔ CONFIRMACIÓN DE RECLAMO
        composable("confirmacionReclamo") {
            ConfirmacionReclamoScreen(navController)
        }
    }
}
