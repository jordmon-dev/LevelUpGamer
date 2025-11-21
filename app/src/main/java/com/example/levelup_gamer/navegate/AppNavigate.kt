package com.example.levelup_gamer.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues // Importado para usar PaddingValues
import androidx.compose.foundation.layout.padding // Importado para usar el modificador .padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier // Importado para usar Modifier
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
            CarritoScreen(navController, carritoViewModel)
        }

        composable("catalogo") {
            CatalogoScreen(navController, productoViewModel, carritoViewModel)
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

        composable("notificaciones") {
            NotificacionScreen(navController)
        }

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
            ReporteReclamoScreen(navController, reclamoViewModel)
        }

        composable("camaraCaptura") {
            CamaraCapturaScreen(navController, reclamoViewModel)
        }

        composable("camaraReclamo") {
            CamaraCapturaScreen(navController, reclamoViewModel)
        }

        composable("confirmacionReclamo") {
            ConfirmacionReclamoScreen(navController, reclamoViewModel)
        }

        // Nota: camaraPerfil fue omitida en el NavHost ya que CamaraPerfilScreen es un ViewModel, no un Composable.
    }
}