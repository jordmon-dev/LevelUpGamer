package com.example.levelup_gamer.ui.screen

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

    // Cargar perfil al iniciar
    LaunchedEffect(Unit) {
        val email = usuarioState.email
        if (email.isNotEmpty()) {
            usuarioViewModel.cargarPerfil(email)
        }
    }

    val nivelNombre = determinarNivel(usuarioState.puntosLevelUp)
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
                    // --- AVATAR ---
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

                    // --- TARJETA DE NIVEL (Recuperada) ---
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
                                        text = nivelNombre,
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
                            LinearProgressIndicator(
                                progress = { progresoNivel },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp),
                                color = Color(0xFF00FF88),
                                trackColor = Color(0xFF2A2A3E),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    // --- ⬇️ BOTONES RECUPERADOS (Cámara, GPS, Reclamos) ⬇️ ---
                    Text(
                        text = "Opciones",
                        color = Color(0xFF00FF88),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Column(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OpcionPerfilNeon(
                            texto = "Notificaciones",
                            color = Color(0xFF00FF88)
                        ) { navController.navigate("notificaciones") }

                        OpcionPerfilNeon(
                            texto = "Reportar Reclamo (Cámara)",
                            color = Color(0xFF00E5FF)
                        ) {
                            // Asegúrate de que esta ruta exista en AppNavigate
                            navController.navigate("reporteReclamo")
                        }

                        OpcionPerfilNeon(
                            texto = "Configurar GPS",
                            color = Color(0xFF00FF88)
                        ) {
                            // Asegúrate de que esta ruta exista en AppNavigate
                            navController.navigate("gps")
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // --- BOTÓN CERRAR SESIÓN ---
                    Button(
                        onClick = {
                            usuarioViewModel.cerrarSesion()
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF4444).copy(alpha = 0.2f),
                            contentColor = Color(0xFFFF4444)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFF4444))
                    ) {
                        Icon(Icons.Default.ExitToApp, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cerrar Sesión")
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }
}

// Función helper para determinar nivel
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

// Componente visual para los botones
@Composable
fun OpcionPerfilNeon(texto: String, color: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF161C2B)),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = texto,
                color = color,
                fontWeight = FontWeight.Medium
            )
        }
    }
}