package com.example.levelup_gamer.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {
    val usuarioState by usuarioViewModel.usuarioState.collectAsState()
    val isLoading by usuarioViewModel.isLoading.collectAsState()

    // 1. Cargar perfil al iniciar la pantalla
    LaunchedEffect(Unit) {
        val email = usuarioState.email
        if (email.isNotEmpty()) {
            // Esta función ya la agregamos al ViewModel en el paso anterior
            usuarioViewModel.cargarPerfil(email)
        }
    }

    // 2. CORRECCIÓN AQUÍ: Usamos 'puntosLevelUp' que es como se llama en el State
    val nivelNombre = determinarNivel(usuarioState.puntosLevelUp)

    // Calculamos progreso para el siguiente nivel (ejemplo simple)
    val progresoNivel = (usuarioState.puntosLevelUp % 500) / 500f

    val brush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0A0A0A), Color(0xFF1A1A2E), Color(0xFF16213E))
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mi Perfil", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
                .padding(padding)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF00FF88)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // --- AVATAR Y DATOS BÁSICOS ---
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(Color(0xFF2A2A3E), CircleShape)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(80.dp),
                            tint = Color(0xFF00FF88)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = usuarioState.nombre.ifEmpty { "Usuario Gamer" },
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = usuarioState.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // --- TARJETA DE NIVEL ---
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Nivel Actual", color = Color.Gray, fontSize = 14.sp)
                                    Text(
                                        text = nivelNombre, // "Oro", "Plata", etc.
                                        color = Color(0xFF00FF88),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 24.sp
                                    )
                                }
                                Surface(
                                    color = Color(0xFF00FF88).copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        "${usuarioState.puntosLevelUp} pts",
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        color = Color(0xFF00FF88),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Barra de Progreso
                            LinearProgressIndicator(
                                progress = { progresoNivel },
                                modifier = Modifier.fillMaxWidth().height(8.dp),
                                color = Color(0xFF00FF88),
                                trackColor = Color(0xFF2A2A3E),
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Próximo nivel en ${500 - (usuarioState.puntosLevelUp % 500)} pts", color = Color.Gray, fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // --- BOTÓN CERRAR SESIÓN ---
                    Button(
                        onClick = {
                            usuarioViewModel.cerrarSesion()
                            // Navegar al login y borrar historial para no poder volver atrás
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF4444).copy(alpha = 0.2f),
                            contentColor = Color(0xFFFF4444)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFFFF4444))
                    ) {
                        Icon(Icons.Default.ExitToApp, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cerrar Sesión")
                    }
                }
            }
        }
    }
}

// Función auxiliar (asegúrate de que esté al final del archivo)
private fun determinarNivel(puntos: Int): String {
    return when {
        puntos >= 2000 -> "Leyenda"
        puntos >= 1000 -> "Diamante"
        puntos >= 500 -> "Oro"
        puntos >= 200 -> "Plata"
        puntos >= 50 -> "Bronce"
        else -> "Principiante"
    }
}