package com.example.levelup_gamer.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.levelup_gamer.ui.theme.Screen.*
import com.example.levelup_gamer.viewmodel.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigate(
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel,
    reclamoViewModel: ReclamoViewModel
) {

    // ViewModels adicionales requeridos por algunas pantallas
    val productoViewModel: ProductoViewModel = viewModel()
    val carritoViewModel: CarritoViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // -----------------------
        // LOGIN Y REGISTRO
        // -----------------------

        composable("login") {
            LoginScreen(navController, usuarioViewModel)
        }

        composable("registro") {
            RegistroScreen(navController, usuarioViewModel)
        }

        // -----------------------
        // HOME + PERFIL + NOTI
        // -----------------------

        composable("home") {
            HomeScreen(navController, usuarioViewModel)
        }

        composable("perfil") {
            PerfilScreen(navController, usuarioViewModel)
        }

        composable("notificaciones") {
            NotificacionScreen(navController)
        }

        // -----------------------
        // CATALOGO
        // -----------------------

        composable("catalogo") {
            CatalogoScreen(
                navController = navController,
                viewModel = productoViewModel,
                carritoViewModel = carritoViewModel
            )
        }

        // -----------------------
        // CARRITO
        // -----------------------

        // CarritoScreen usa viewModels internos así que NO pasamos nada
        composable("carrito") {
            CarritoScreen(navController)
        }

        // -----------------------
        // RECLAMOS
        // -----------------------

        composable("reporteReclamo") {
            ReporteReclamoScreen(navController, reclamoViewModel)
        }

        composable("camaraCaptura") {
            CamaraCapturaScreen(navController, reclamoViewModel)
        }

        composable("confirmacionReclamo") {
            ConfirmacionReclamoScreen(navController)
        }

        // -----------------------
        // PAGO (botón desde carrito)
        // -----------------------
        composable("pago") {
            PagoScreen(navController)
        }
    }
}
