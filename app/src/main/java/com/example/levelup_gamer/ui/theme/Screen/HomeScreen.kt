package com.example.levelup_gamer.ui.theme.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavController,
    viewModel: UsuarioViewModel = viewModel()
) {
    val usuario by viewModel.usuario.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Level-Up Gamer", color = Color(0xFF1E90FF)) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1A1A1A)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 💬 Bienvenida
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        if (usuario.nombre.isNotEmpty())
                            "¡Bienvenido, ${usuario.nombre}!"
                        else "¡Bienvenido a Level-Up Gamer!",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Level-Up Gamer",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color(0xFF1E90FF),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Tu tienda gaming de confianza",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    if (usuario.correo.endsWith("@duocuc.cl")) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF39FF14).copy(alpha = 0.2f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.School,
                                    contentDescription = "Estudiante",
                                    tint = Color(0xFF39FF14)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "20% descuento estudiante Duoc activo",
                                    color = Color(0xFF39FF14),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🔹 Tarjetas de navegación principales
            NavigationCard(
                icon = Icons.Default.Storefront,
                title = "📱 Catálogo de Productos",
                description = "Explora todos nuestros productos gaming",
                onClick = { navController.navigate("catalogo") },
                color = Color(0xFF1E90FF)
            )

            NavigationCard(
                icon = Icons.Default.Person,
                title = "👤 Mi Perfil",
                description = "Gestiona tu cuenta y beneficios",
                onClick = { navController.navigate("perfil") },
                color = Color(0xFF39FF14)
            )

            NavigationCard(
                icon = Icons.Default.ShoppingCart,
                title = "🛒 Carrito de Compras",
                description = "Revisa tus productos seleccionados",
                onClick = { navController.navigate("carrito") },
                color = Color(0xFFFFD700)
            )

            NavigationCard(
                icon = Icons.Default.Info,
                title = "ℹ️ Acerca de",
                description = "Conoce más sobre Level-Up Gamer",
                onClick = { navController.navigate("about") },
                color = Color(0xFFFF6B6B)
            )

            // 🔔 NUEVAS PANTALLAS (correctamente ubicadas)
            NavigationCard(
                icon = Icons.Default.Notifications,
                title = "🔔 Notificaciones",
                description = "Consulta tus alertas, ofertas y mensajes",
                onClick = { navController.navigate("notificaciones") },
                color = Color(0xFF1E90FF)
            )

            NavigationCard(
                icon = Icons.Default.Help,
                title = "❓ Centro de Ayuda",
                description = "Soporte técnico y preguntas frecuentes",
                onClick = { navController.navigate("ayuda") },
                color = Color(0xFF39FF14)
            )

            // NUEVA TARJETA DE CÁMARA
            NavigationCard(
                icon = Icons.Default.CameraAlt,
                title = "📸 Cámara de Captura",
                description = "Toma una foto con la cámara",
                onClick = { navController.navigate("camaraCaptura") },
                color = Color(0xFFFFD700)
            )

            //TARJETA PARA GPS

            NavigationCard(
                icon = Icons.Default.LocationOn,
                title = "🧭 GPS",
                description = "Muestra tu ubicación actual",
                onClick = { navController.navigate("gps") },
                color = Color(0xFF1E90FF)
            )


            Spacer(modifier = Modifier.height(24.dp))

            // 🚪 Botón de cierre de sesión
            OutlinedButton(
                onClick = {
                    viewModel.limpiarEstado()
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
            ) {
                Icon(Icons.Default.Logout, contentDescription = "Cerrar sesión")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar Sesión")
            }
        }
    }
}

/** 🔸 Composable reutilizable para las tarjetas de navegación */
@Composable
fun NavigationCard(
    icon: ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit,
    color: Color
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyLarge, color = Color.White, fontWeight = FontWeight.Medium)
                Text(description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = "Ir", tint = Color.Gray, modifier = Modifier.size(20.dp))
        }
    }
}
