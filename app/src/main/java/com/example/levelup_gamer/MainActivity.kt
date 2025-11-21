package com.example.levelup_gamer

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.levelup_gamer.ui.theme.AppNavigate
import com.example.levelup_gamer.viewmodel.ReclamoViewModel
import com.example.levelup_gamer.viewmodel.UsuarioViewModel
import com.example.levelup_gamer.ui.theme.theme.LevelUpGamerTheme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LevelUpGamerTheme {

                // Controlador de navegación
                val navController = rememberNavController()

                // ViewModels principales (los que AppNavigate necesita)
                val reclamoViewModel: ReclamoViewModel = viewModel()
                val usuarioViewModel: UsuarioViewModel = viewModel()
                LaunchedEffect(Unit) {
                    usuarioViewModel.cargarDatosGuardados()
                }

                // Navegación principal
                AppNavigate(
                    navController = navController,
                    usuarioViewModel = usuarioViewModel,
                    reclamoViewModel = reclamoViewModel
                )
            }
        }
    }
}
