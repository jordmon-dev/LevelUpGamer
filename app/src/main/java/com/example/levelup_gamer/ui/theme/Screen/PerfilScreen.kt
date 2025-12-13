package com.example.levelup_gamer.ui.theme.Screen

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState // Asegúrate de tener este import
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.UsuarioViewModel
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {
    // Asegúrate de tener el import correcto para collectAsState
    val usuarioState by usuarioViewModel.usuarioState.collectAsState()
    val isLoading by usuarioViewModel.isLoading.collectAsState()

    // Cargar perfil al iniciar la pantalla
    LaunchedEffect(Unit) {
        usuarioViewModel.cargarPerfil()
    }

    val fondo = Brush.verticalGradient(
        listOf(
            Color(0xFF050510),
            Color(0xFF0B1020),
            Color(0xFF11162B),
            Color(0xFF050510)
        )
    )

    val verdeNeon = Color(0xFF00FF88)
    val cianNeon = Color(0xFF00E5FF)
    val cardBg = Color(0xFF101526)

    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mi Perfil", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = cianNeon
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->

        Box(
            Modifier
                .fillMaxSize()
                .background(fondo)
                .padding(innerPadding)
        ) {
            if (isLoading) {
                // Mostrar indicador de carga
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = verdeNeon)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Cargando perfil...", color = Color.White)
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(Modifier.height(16.dp))

                    // AVATAR
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(cardBg, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = verdeNeon,
                            modifier = Modifier.size(70.dp)
                        )
                    }

                    Spacer(Modifier.height(14.dp))

                    // Usar if-else para manejar strings vacíos
                    Text(
                        text = if (usuarioState.nombre.isNotEmpty()) usuarioState.nombre else "Invitado",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Text(
                        text = if (usuarioState.email.isNotEmpty()) usuarioState.email else "No registrado",
                        color = cianNeon,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(Modifier.height(30.dp))

                    // TARJETA DE INFO
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(cardBg),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Column(Modifier.padding(20.dp)) {

                            // Nivel basado en puntos
                            val nivel = determinarNivel(usuarioState.puntosFidelidad)
                            Text(
                                "Nivel: $nivel",
                                color = verdeNeon,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                "Puntos Fidelidad: ${usuarioState.puntosFidelidad}",
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                "Miembro desde: ${if (usuarioState.fechaRegistro.isNotEmpty()) usuarioState.fechaRegistro else "2024"}",
                                color = cianNeon
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            // Estado de Cuenta
                            Text(
                                "Estado de Cuenta:",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            val esEstudianteDuoc = usuarioState.email.endsWith("@duocuc.cl", ignoreCase = true)
                            Text(
                                text = if (esEstudianteDuoc) {
                                    "✅ Estudiante DUOCUC (20% Descuento)"
                                } else {
                                    "👤 Usuario Regular (10% Descuento)"
                                },
                                color = if (esEstudianteDuoc) verdeNeon else cianNeon,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(34.dp))

                    // SECCIÓN OPCIONES
                    Text(
                        text = "Opciones",
                        color = verdeNeon,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Column(Modifier.fillMaxWidth(), Arrangement.spacedBy(10.dp)) {

                        OpcionPerfilNeon(
                            texto = "Notificaciones",
                            color = verdeNeon
                        ) { navController.navigate("notificaciones") }

                        OpcionPerfilNeon(
                            texto = "Reportar Reclamo",
                            color = cianNeon
                        ) { navController.navigate("reporteReclamo") }

                        OpcionPerfilNeon(
                            texto = "Configurar GPS",
                            color = verdeNeon
                        ) { navController.navigate("gps") }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // CERRAR SESIÓN
                    Button(
                        onClick = {
                            usuarioViewModel.cerrarSesion()
                            navController.navigate("login") {
                                popUpTo("home") { inclusive = true }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD00036),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(Icons.Default.ExitToApp, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cerrar sesión")
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

// Función helper para determinar nivel basado en puntos
private fun determinarNivel(puntos: Int): String {
    return when {
        puntos >= 1000 -> "Diamante"
        puntos >= 500 -> "Oro"
        puntos >= 200 -> "Plata"
        puntos >= 50 -> "Bronce"
        else -> "Principiante"
    }
}

@Composable
fun OpcionPerfilNeon(texto: String, color: Color, onClick: () -> Unit) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(Color(0xFF161C2B)),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            Modifier.fillMaxWidth().padding(18.dp),
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