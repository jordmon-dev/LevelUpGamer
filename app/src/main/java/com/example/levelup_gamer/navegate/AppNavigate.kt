package com.example.levelup_gamer.navegate

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text // Para el placeholder de notificaciones
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// --- IMPORTS DE TUS PANTALLAS ---
import com.example.levelup_gamer.screen.*
//import com.example.levelup_gamer.ui.screen.AdminAgregarProductoScreen

// --- IMPORTS DE VIEWMODELS ---
import com.example.levelup_gamer.viewmodel.UsuarioViewModel
import com.example.levelup_gamer.viewmodel.ProductoViewModel
import com.example.levelup_gamer.viewmodel.CarritoViewModel
import com.example.levelup_gamer.viewmodel.OfertasViewModel
import com.example.levelup_gamer.viewmodel.ReclamoViewModel // ¡IMPORTANTE!

@Composable
fun AppNavigate(
    navController: androidx.navigation.NavHostController = androidx.navigation.compose.rememberNavController()
    ) {


    //val navController = rememberNavController()

    // --- INSTANCIAS DE VIEWMODELS ---
    // (Necesarias para que funcionen tus pantallas nuevas)
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val productoViewModel: ProductoViewModel = viewModel()
    val carritoViewModel: CarritoViewModel = viewModel()
    val ofertasViewModel: OfertasViewModel = viewModel()
    val reclamoViewModel: ReclamoViewModel = viewModel() // Nueva instancia para tus pantallas de reclamo

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // --- 1. AUTENTICACIÓN ---
        composable("login") {
            LoginScreen(navController, usuarioViewModel)
        }

        composable("recuperar_password") {
            RecuperarPasswordScreen(navController)
        }

        composable("registro") {
            RegistroScreen(navController, usuarioViewModel)
        }

        // --- 2. PANTALLAS PRINCIPALES ---
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

        // --- NUEVA RUTA: Detalle de Producto ---
        composable("detalle_producto/{productoId}") { backStackEntry ->
            // Recuperamos el ID que pasamos en la ruta
            val idString = backStackEntry.arguments?.getString("productoId")
            val id = idString?.toIntOrNull() ?: 0

            DetalleProductoScreen(
                navController = navController,
                productoId = id, // Pasamos el ID a la pantalla
                productoViewModel = productoViewModel,
                carritoViewModel = carritoViewModel
            )
        }

        composable("perfil") {
            PerfilScreen(navController, usuarioViewModel)
        }

        // --- 3. PANTALLAS SECUNDARIAS ---
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

        // --- 4. NUEVAS PANTALLAS (INTEGRACIÓN NATIVA) ---

        // Pantalla Principal de Reclamo (Pide Foto y Descripción)
        composable("reporteReclamo") {
            ReporteReclamoScreen(navController, reclamoViewModel)
        }

        // Pantalla de Cámara (Llama a los permisos y usa el hardware)
        composable("camaraReclamo") {
            CamaraReclamoScreen(navController, reclamoViewModel)
        }

        // Pantalla de GPS (Mapa y Ubicación)
        // Nota: En tu archivo se llama 'PantallaGpsScreen', aquí lo conectamos a la ruta "gps"
        composable("gps") {
            PantallaGpsScreen(navController, reclamoViewModel)
        }

        // Pantalla de Confirmación (Éxito al enviar)
        composable("confirmacionReclamo") {
            ConfirmacionReclamoScreen(navController, reclamoViewModel)
        }

        // Pantalla de Admin
        composable("admin_agregar_producto") {
            // Si no tienes este archivo, comenta estas 3 líneas para que no de error
            AdminAgregarProductoScreen(navController, productoViewModel)
        }

        // Placeholder para Notificaciones (Para que no crashee si le das click en perfil)
        composable("notificaciones") {
            // ✅ AHORA SÍ LLAMAMOS A LA PANTALLA REAL
            NotificacionScreen(navController)
        }

    }
}