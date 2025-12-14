package com.example.levelup_gamer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

// --- IMPORTANTE: Asegúrate de que estos imports no den error ---
import com.example.levelup_gamer.navegate.AppNavigate
import com.example.levelup_gamer.ui.theme.components.BottomBar
import com.example.levelup_gamer.ui.theme.theme.LevelUpGamerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LevelUpGamerTheme {
                // 1. Creamos el controlador de navegación principal
                val navController = rememberNavController()

                // 2. Detectamos en qué pantalla estamos
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // 3. Decidimos si mostrar la barra o no
                // NOTA: "home", "catalogo", etc. deben coincidir EXACTAMENTE con AppNavigate
                val showBottomBar = currentRoute in listOf("home", "catalogo", "carrito", "perfil")

                Scaffold(
                    containerColor = Color(0xFF050510), // Fondo oscuro base

                    // 4. Aquí colocamos la barra condicionalmente
                    bottomBar = {
                        if (showBottomBar) {
                            BottomBar(
                                navController = navController,
                                currentRoute = currentRoute
                            )
                        }
                    }
                ) { paddingValues ->
                    // 5. El contenido (AppNavigate) debe respetar el espacio de la barra (paddingValues)
                    Box(modifier = Modifier.padding(paddingValues)) {
                        AppNavigate()
                    }
                }
            }
        }
    }
}