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
// ⚠️ ESTA LÍNEA ES CLAVE: Debe coincidir con la carpeta 'navegate'
import com.example.levelup_gamer.navegate.AppNavigate
import com.example.levelup_gamer.ui.theme.components.BottomBar
import com.example.levelup_gamer.ui.theme.theme.LevelUpGamerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LevelUpGamerTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val showBottomBar = currentRoute in listOf("home", "catalogo", "carrito", "perfil")

                Scaffold(
                    containerColor = Color(0xFF0A0A0A),
                    bottomBar = {
                        if (showBottomBar) {
                            BottomBar(navController = navController, currentRoute = currentRoute)
                        }
                    }
                ) { paddingValues ->
                    // Contenedor para respetar el espacio del menú
                    Box(modifier = Modifier.padding(paddingValues)) {
                        // Llamada sin argumentos (porque los ViewModels se crean dentro)
                        AppNavigate()
                    }
                }
            }
        }
    }
}