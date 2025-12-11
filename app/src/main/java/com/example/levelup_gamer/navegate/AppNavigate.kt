package com.example.levelup_gamer.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.levelup_gamer.ui.theme.Screen.AboutScreen
import com.example.levelup_gamer.ui.theme.Screen.AyudaScreen
import com.example.levelup_gamer.ui.theme.Screen.CamaraReclamoScreen
import com.example.levelup_gamer.ui.theme.Screen.CarritoScreen
import com.example.levelup_gamer.ui.theme.Screen.ConfirmacionReclamoScreen
import com.example.levelup_gamer.ui.theme.Screen.ConfirmacionScreen
import com.example.levelup_gamer.ui.theme.Screen.HomeScreen
import com.example.levelup_gamer.ui.theme.Screen.NotificacionScreen
import com.example.levelup_gamer.ui.theme.Screen.OfertasScreen
import com.example.levelup_gamer.ui.theme.Screen.PagoScreen
import com.example.levelup_gamer.ui.theme.Screen.PantallaGpsScreen
import com.example.levelup_gamer.ui.theme.Screen.PerfilScreen
import com.example.levelup_gamer.ui.theme.Screen.ReporteReclamoScreen
import com.example.levelup_gamer.ui.theme.screen.*

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
    pagoViewModel: PagoViewModel,
    innerPadding: PaddingValues // Nuevo parámetro para el padding del Scaffold
) {

    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = Modifier.padding(innerPadding) // Aplicar el padding del Scaffold
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

        // RUTAS CON BARRA DE NAVEGACIÓN
        composable("perfil") {
            PerfilScreen(navController, usuarioViewModel)
        }

        composable("carrito") {
            // CORREGIDO: Pasamos el usuarioViewModel explícitamente
            CarritoScreen(navController, carritoViewModel, usuarioViewModel)
        }

        composable("catalogo") {
            // CORREGIDO: Obtenemos el email y se lo pasamos a la pantalla
            val usuarioState by usuarioViewModel.usuario.collectAsState()
            CatalogoScreen(
                navController = navController,
                viewModel = productoViewModel,
                carritoViewModel = carritoViewModel,
                usuarioEmail = usuarioState.email
            )
        }

        composable("notificaciones") {
            NotificacionScreen(navController)
        }

        // RUTAS DE INFORMACIÓN Y SOPORTE (Desde HomeScreen)
        composable("about") {
            AboutScreen(navController)
        }

        composable("ayuda") {
            AyudaScreen(navController)
        }

        // RUTAS MISCELÁNEAS (Desde Perfil y otras)
        composable("ofertas") {
            // Nota: Se crea una instancia simple de ViewModel si no se pasa desde MainActivity
            OfertasScreen(navController, OfertasViewModel())
        }

        // ELIMINADO: Se borró la ruta "notificaciones" duplicada

        composable("pago") {
            PagoScreen(navController, carritoViewModel)
        }

        composable("confirmacion") {
            ConfirmacionScreen(navController)
        }

        composable("gps") {
            PantallaGpsScreen(navController, reclamoViewModel)
        }


        // RUTAS DE RECLAMOS
        composable("reporteReclamo") {
            ReporteReclamoScreen(
                navController = navController,
                reclamoViewModel = reclamoViewModel
            )
        }

        composable("camaraReclamo") {
            CamaraReclamoScreen(
                navController = navController,
                reclamoViewModel = reclamoViewModel
            )
        }

        composable("confirmacionReclamo") {
            ConfirmacionReclamoScreen(navController, reclamoViewModel)
        }
    }
}

@Composable
fun RegistroScreen(x0: NavHostController, x1: UsuarioViewModel) {
    TODO("Not yet implemented")
}

@Composable
fun LoginScreen(x0: NavHostController, x1: UsuarioViewModel) {
    TODO("Not yet implemented")
}