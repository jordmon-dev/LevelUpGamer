// PerfilScreen.kt - VERSIÓN SIMPLIFICADA
package com.example.levelup_gamer.ui.theme.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.levelup_gamer.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel = viewModel()
) {
    val usuarioPerfil = usuarioViewModel.toUsuarioPerfil()
    val fotoPerfilUri by usuarioViewModel.fotoPerfilUri.collectAsState()

    val fondo = Brush.verticalGradient(
        listOf(
            Color(0xFF050510),
            Color(0xFF0B1020),
            Color(0xFF11162B),
            Color(0xFF050510)
        )
    )

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
                            tint = Color.White
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(Modifier.height(16.dp))

                // FOTO DE PERFIL - Clickable para cambiar
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .clickable {
                            // Navegar a la pantalla de cámara de perfil
                            navController.navigate("camaraPerfil")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    // Contenedor circular de la imagen
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1B2338)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (fotoPerfilUri != null) {
                            // Mostrar la foto de perfil
                            AsyncImage(
                                model = fotoPerfilUri,
                                contentDescription = "Foto de perfil",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Usuario",
                                tint = Color(0xFF00E5FF),
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }

                    // Badge de cámara
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(36.dp)
                            .background(Color(0xFF00E5FF), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.CameraAlt,
                            contentDescription = "Cambiar foto",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Texto indicador
                Text(
                    text = "Toca para cambiar foto",
                    color = Color(0xFFB0BEC5),
                    style = MaterialTheme.typography.labelSmall
                )

                Spacer(Modifier.height(18.dp))

                // NOMBRE
                Text(
                    text = usuarioPerfil.nombre,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                // CORREO
                Text(
                    text = usuarioPerfil.email,
                    color = Color(0xFFB0BEC5),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(26.dp))

                // CARD DE INFO
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(Color(0xFF151A2A)),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Text(
                            "Nivel: ${usuarioPerfil.nivel}",
                            color = Color(0xFF00E5FF),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            "Puntos Level-Up: ${usuarioPerfil.puntosLevelUp}",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            "Miembro desde: ${usuarioPerfil.fechaRegistro}",
                            color = Color(0xFFB0BEC5),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(Modifier.height(32.dp))

                // OPCIONES
                Text(
                    text = "Opciones",
                    color = Color(0xFF00E5FF),
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(14.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    BotonOpcionPerfil("Ver Carrito") {
                        navController.navigate("carrito")
                    }

                    BotonOpcionPerfil("Ver Notificaciones") {
                        navController.navigate("notificaciones")
                    }

                    BotonOpcionPerfil("Reportar Reclamo") {
                        navController.navigate("reporteReclamo")
                    }

                    BotonOpcionPerfil("Configurar Dirección GPS") {
                        navController.navigate("gps")
                    }
                }

                Spacer(Modifier.height(32.dp))

                // BOTÓN CERRAR SESIÓN
                Button(
                    onClick = {
                        usuarioViewModel.cerrarSesion()
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE53935),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Cerrar sesión", fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun BotonOpcionPerfil(texto: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(Color(0xFF1B2335)),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = texto,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}