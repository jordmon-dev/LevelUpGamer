package com.example.levelup_gamer.navegate

import ConfirmacionScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
// Importamos todas las pantallas del paquete correcto
import com.example.levelup_gamer.ui.theme.Screen.*
import com.example.levelup_gamer.viewmodel.UsuarioViewModel
import com.example.levelup_gamer.viewmodel.ProductoViewModel
import com.example.levelup_gamer.viewmodel.CarritoViewModel
import com.example.levelup_gamer.viewmodel.AboutViewModel


@Composable
fun AppNavigate(){
    val navController = rememberNavController()

    // Inyección de ViewModels al inicio para que persistan en toda la aplicación
    val usuarioViewModel: UsuarioViewModel = viewModel()
    val productoViewModel: ProductoViewModel = viewModel()
    val carritoViewModel: CarritoViewModel = viewModel()
    val aboutViewModel: AboutViewModel = viewModel()

    NavHost(
        navController = navController,
        // La aplicación siempre comienza en la pantalla de Login
        startDestination = "login"
    )
    {
        // 1. LOGIN
        composable(route = "login") {
            // Pasamos navController y el ViewModel
            LoginScreen(navController = navController, viewModel = usuarioViewModel)
        }

        // 2. REGISTRO
        composable(route = "registro") {
            // Pasamos navController y el ViewModel
            RegistroScreen(navController = navController, viewModel = usuarioViewModel)
        }

        // 3. HOME (Destino después de un Login/Registro exitoso)
        composable(route = "home") {
            Home(navController = navController)
        }

        // 4. BIENVENIDA (Asumo que esta es la pantalla de Bienvenida de prueba)
        composable(route="bienvenida") {
            BienvenidaScreen(viewModel = usuarioViewModel)
        }

        // 5. CATÁLOGO
        composable(route = "catalogo") {
            // Pasar ambos ViewModels al catálogo
            Catalogo(
                navController = navController,
                viewModel = productoViewModel,
                carritoViewModel = carritoViewModel
            )
        }

        // 6. PERFIL
        composable(route = "perfil") {
            // Pasamos el UsuarioViewModel para ver/editar perfil
            Perfil(navController = navController, viewModel = usuarioViewModel)
        }

        // 7. CARRITO
        composable(route = "carrito") {
            // Pasamos el CarritoViewModel
            Carrito(navController = navController, viewModel = carritoViewModel)
        }


        // 8. ACERCA DE
        composable(route = "about") {
            // Pasamos el AboutViewModel
            About(navController = navController, viewModel = aboutViewModel)
        }

        // 9. PAGO
        composable(route = "pago") {
            PagoScreen(navController = navController, carritoViewModel = carritoViewModel)
        }

        // 10. CONFIRMACIÓN
        composable(route = "confirmacion") {
            ConfirmacionScreen(navController = navController)
        }
    }
}


