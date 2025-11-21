package com.example.levelup_gamer.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.levelup_gamer.ui.theme.Screen.*

import com.example.levelup_gamer.viewmodel.*

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavigate(
    navController: NavHostController,
    usuarioViewModel: UsuarioViewModel,
    productoViewModel: ProductoViewModel,
    carritoViewModel: CarritoViewModel,
    reclamoViewModel: ReclamoViewModel,
    notificacionesViewModel: NotificacionesViewModel,
    pagoViewModel: PagoViewModel
) {

    NavHost(
        navController = navController,
        startDestination = "login"   // ← CORREGIDO
    ) {

        // LOGIN PRIMERO
        composable("login") {
            LoginScreen(navController, usuarioViewModel)
        }

        composable("registro") {
            RegistroScreen(navController, usuarioViewModel)
        }

        // HOME DESPUÉS DEL LOGIN
        composable("home") {
            HomeScreen(navController, productoViewModel, carritoViewModel, usuarioViewModel)
        }

        composable("perfil") {
            PerfilScreen(navController, usuarioViewModel)
        }

        composable("carrito") {
            CarritoScreen(navController, carritoViewModel)
        }

        // RECLAMOS
        composable("reporteReclamo") {
            ReporteReclamoScreen(navController, reclamoViewModel)
        }

        composable("camaraCaptura") {
            CamaraCapturaScreen(navController, reclamoViewModel)
        }

        // ESTA ES LA QUE USA ReporteReclamoScreen
        composable("camaraReclamo") {
            CamaraCapturaScreen(navController, reclamoViewModel)
        }

        composable("camaraPerfil") {
            CamaraPerfilScreen(navController, usuarioViewModel)
        }

        composable("confirmarReclamo") {
            ConfirmacionReclamoScreen(navController, reclamoViewModel)
        }
    }
}
