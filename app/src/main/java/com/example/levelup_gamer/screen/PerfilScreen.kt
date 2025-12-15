package com.example.levelup_gamer.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.UsuarioViewModel
import androidx.compose.ui.graphics.graphicsLayer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {
    val usuarioState by usuarioViewModel.usuarioState.collectAsState()
    val isLoading by usuarioViewModel.isLoading.collectAsState()

    // Lógica para detectar si es ADMIN (Truco para la demo)
    val esAdmin = usuarioState.email.contains("admin", ignoreCase = true) ||
            usuarioState.email.contains("profesor", ignoreCase = true)

    // Cargar perfil al iniciar
    LaunchedEffect(Unit) {
        if (usuarioState.email.isNotEmpty()) {
            usuarioViewModel.cargarPerfil(usuarioState.email)
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
                    // --- SECCIÓN 1: CABECERA ---
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .background(Color(0xFF2A2A3E), CircleShape)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(70.dp),
                            tint = Color(0xFF00FF88)
                        )
                        // Badge Admin
                        if (esAdmin) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .background(Color(0xFFFFD700), CircleShape)
                                    .padding(6.dp)
                            ) {
                                Icon(Icons.Default.Star, null, tint = Color.Black, modifier = Modifier.size(16.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = usuarioState.nombre.ifEmpty { "Gamer Desconocido" },
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = usuarioState.email,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // --- SECCIÓN 2: TARJETA DE NIVEL ---
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
                                    Text("Rango Actual", color = Color.Gray, fontSize = 12.sp)
                                    Text(
                                        text = nivelNombre,
                                        color = Color(0xFF00FF88),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 22.sp
                                    )
                                }
                                Surface(
                                    color = Color(0xFF00FF88).copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        "${usuarioState.puntosLevelUp} XP",
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        color = Color(0xFF00FF88),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            LinearProgressIndicator(
                                progress = { progresoNivel },
                                modifier = Modifier.fillMaxWidth().height(6.dp),
                                color = Color(0xFF00FF88),
                                trackColor = Color(0xFF2A2A3E),
                            )
                            Text("Próximo nivel en ${500 - (usuarioState.puntosLevelUp % 500)} XP", color = Color.Gray, fontSize = 10.sp, modifier = Modifier.padding(top=4.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // --- SECCIÓN 3: MIS PEDIDOS (NUEVO) ---
                    Text(
                        text = "Mis Pedidos Recientes",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
                    )

                    // Lista simulada de pedidos
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        PedidoItem("PED-001", "20/12/2025", "$ 45.990", "Entregado", Color(0xFF00FF88))
                        PedidoItem("PED-002", "22/12/2025", "$ 12.990", "En Camino", Color(0xFFFFD700))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // --- SECCIÓN 4: OPCIONES ---
                    Text(
                        text = "Configuración",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        // BOTÓN ADMIN (SOLO VISIBLE SI ES ADMIN)
                        if (esAdmin) {
                            OpcionPerfilNeon(
                                texto = "Panel de Administrador",
                                icono = Icons.Default.AdminPanelSettings,
                                color = Color(0xFFFFD700) // Dorado para resaltar
                            ) { navController.navigate("admin_agregar_producto") }
                        }

                        OpcionPerfilNeon("Notificaciones", Icons.Default.Notifications) { navController.navigate("notificaciones") }
                        OpcionPerfilNeon("Cámara / Reclamos", Icons.Default.CameraAlt) { navController.navigate("reporteReclamo") }
                        OpcionPerfilNeon("Mi Ubicación (GPS)", Icons.Default.LocationOn) { navController.navigate("gps") }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Botón Cerrar Sesión
                    OutlinedButton(
                        onClick = {
                            usuarioViewModel.cerrarSesion()
                            navController.navigate("login") { popUpTo(0) { inclusive = true } }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFF4444)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFF4444))
                    ) {
                        Icon(Icons.Default.ExitToApp, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cerrar Sesión")
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}

// Sub-componentes para limpiar el código principal
@Composable
fun PedidoItem(id: String, fecha: String, total: String, estado: String, colorEstado: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A3E)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Orden #$id", color = Color.White, fontWeight = FontWeight.Bold)
                Text(fecha, color = Color.Gray, fontSize = 12.sp)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(total, color = Color(0xFF00FF88), fontWeight = FontWeight.Bold)
                Text(estado, color = colorEstado, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun OpcionPerfilNeon(
    texto: String,
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color = Color.White,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icono, null, tint = Color(0xFF00FF88), modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(texto, color = color, modifier = Modifier.weight(1f))
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.Gray, modifier = Modifier.size(16.dp).rotate(180f)) // Flechita
        }
    }
}

// Función helper
private fun determinarNivel(puntos: Int): String {
    return when {
        puntos >= 2000 -> "Leyenda"
        puntos >= 1000 -> "Diamante"
        puntos >= 500 -> "Oro"
        else -> "Principiante"
    }
}
// Necesitas esta extensión para rotar la flecha
fun Modifier.rotate(degrees: Float) = this.then(Modifier.graphicsLayer(rotationZ = degrees))
