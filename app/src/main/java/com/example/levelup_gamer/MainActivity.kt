package com.example.levelup_gamer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.levelup_gamer.navigation.AppNavigate
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

                AppNavigate(
                    navController = navController,
                    usuarioViewModel = usuarioViewModel,
                    productoViewModel = productoViewModel,
                    carritoViewModel = carritoViewModel,
                    reclamoViewModel = reclamoViewModel,
                    notificacionesViewModel = notificacionesViewModel,
                    pagoViewModel = pagoViewModel
                )
            }
        }
    }
}
