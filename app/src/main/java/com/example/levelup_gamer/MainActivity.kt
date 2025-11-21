package com.example.levelup_gamer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold // ⬅️ IMPORT FALTANTE 1
import androidx.compose.runtime.getValue // ⬅️ IMPORT FALTANTE 2
import androidx.compose.ui.graphics.Color // ⬅️ IMPORT FALTANTE 3
import androidx.navigation.compose.currentBackStackEntryAsState // ⬅️ IMPORT FALTANTE 4
import androidx.navigation.compose.rememberNavController
import com.example.levelup_gamer.navigation.AppNavigate
import com.example.levelup_gamer.ui.theme.components.BottomBar // ⬅️ IMPORT FALTANTE 5 (BottomBar)
import com.example.levelup_gamer.ui.theme.theme.LevelUpGamerTheme
import com.example.levelup_gamer.viewmodel.*

class MainActivity : ComponentActivity() {

    // ViewModels compartidos en toda la app
    private val usuarioViewModel: UsuarioViewModel by viewModels()
    private val productoViewModel: ProductoViewModel by viewModels()
    private val carritoViewModel: CarritoViewModel by viewModels()
    private val reclamoViewModel: ReclamoViewModel by viewModels()
    private val notificacionesViewModel: NotificacionesViewModel by viewModels()
    private val pagoViewModel: PagoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LevelUpGamerTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // Rutas donde la barra de navegación debe ser visible
                val showBottomBar = currentRoute in listOf("home", "catalogo", "carrito", "perfil")

                Scaffold(
                    // Usar un color de fondo oscuro para que coincida con el tema (opcional, pero buena práctica)
                    containerColor = Color(0xFF0A0A0A),
                    bottomBar = {
                        if (showBottomBar) {
                            BottomBar(navController = navController, currentRoute = currentRoute)
                        }
                    }
                ) { paddingValues ->
                    AppNavigate(
                        navController = navController,
                        usuarioViewModel = usuarioViewModel,
                        productoViewModel = productoViewModel,
                        carritoViewModel = carritoViewModel,
                        reclamoViewModel = reclamoViewModel,
                        notificacionesViewModel = notificacionesViewModel,
                        pagoViewModel = pagoViewModel,
                        innerPadding = paddingValues // Pasar el padding al NavHost
                    )
                }
            }
        }
    }
}