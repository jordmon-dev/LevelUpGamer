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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {
    val usuario = usuarioViewModel.usuario.collectAsState().value

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

                Text(
                    text = usuario.nombre,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = usuario.email,
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

                        Text(
                            "Nivel: ${usuario.nivel}",
                            color = verdeNeon,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(10.dp))

                        Text(
                            "Puntos Level-Up: ${usuario.puntosLevelUp}",
                            color = Color.White
                        )
                        Spacer(Modifier.height(10.dp))

                        Text(
                            "Miembro desde: ${usuario.fechaRegistro}",
                            color = cianNeon
                        )
                    }
                }

                Spacer(Modifier.height(34.dp))

                // SECCIÓN OPCIONES
                Text(
                    text = "Opciones",
                    color = verdeNeon,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(14.dp))

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

                Spacer(Modifier.height(32.dp))

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
                    Spacer(Modifier.width(8.dp))
                    Text("Cerrar sesión")
                }

                Spacer(Modifier.height(24.dp))
            }
        }
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
