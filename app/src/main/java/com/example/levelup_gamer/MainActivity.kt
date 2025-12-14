package com.example.levelup_gamer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.levelup_gamer.navegate.AppNavigate
import com.example.levelup_gamer.ui.theme.theme.LevelUpGamerTheme
import com.example.levelup_gamer.ui.theme.components.BottomBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LevelUpGamerTheme {
                // 1. Controlador de Navegación Principal
                val navController = rememberNavController()

                // 2. Detectar la ruta actual para saber si mostrar la barra
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // 3. Lista de pantallas donde SÍ queremos ver la barra
                val showBottomBar = currentRoute in listOf(
                    "home",
                    "catalogo",
                    "carrito",
                    "perfil"
                )

                Scaffold(
                    containerColor = Color(0xFF0A0A0A), // Fondo base oscuro
                    modifier = Modifier.fillMaxSize(),

                    // 4. Aquí está la MAGIA: La barra se muestra solo si showBottomBar es true
                    bottomBar = {
                        if (showBottomBar) {
                            BottomBar(
                                navController = navController,
                                currentRoute = currentRoute
                            )
                        }
                    }
                ) { paddingValues ->
                    // 5. El contenido (AppNavigate) respeta el espacio de la barra
                    Box(modifier = Modifier.padding(paddingValues)) {
                        AppNavigate(navController = navController)
                }
            }
        }
    }
}
}