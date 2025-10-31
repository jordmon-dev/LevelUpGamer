package com.example.levelup_gamer.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelup_gamer.model.UsuarioPerfil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Perfil(navController: NavController) {
    var usuario by remember {
        mutableStateOf(
            UsuarioPerfil(
                nombre = "Juan Pérez",
                email = "juan.perez@duocuc.cl",
                puntosLevelUp = 1500,
                nivel = "Gamer Pro",
                fechaRegistro = "15/03/2024"
            )
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mi Perfil") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Editar perfil */ }) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar y info básica
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Usuario",
                        tint = Color(0xFF1E90FF),
                        modifier = Modifier.size(80.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        usuario.nombre,
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )

                    Text(
                        usuario.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Nivel y puntos
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InfoChip(
                            icon = Icons.Default.Star,
                            text = "Nivel ${usuario.nivel}",
                            color = Color(0xFF39FF14)
                        )
                        InfoChip(
                            icon = Icons.Default.Star,
                            text = "${usuario.puntosLevelUp} pts",
                            color = Color(0xFF1E90FF)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Información adicional
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Información de la cuenta",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    InfoRow("Miembro desde:", usuario.fechaRegistro)
                    InfoRow("Estado:", "Activo")
                    InfoRow("Descuento:", "20% (Estudiante Duoc)")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Beneficios Level-Up
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "🎮 Beneficios Level-Up",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF39FF14)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    BenefitItem("✓ Descuento exclusivo para estudiantes Duoc")
                    BenefitItem("✓ Acumulación de puntos por compras")
                    BenefitItem("✓ Acceso a ofertas especiales")
                    BenefitItem("✓ Soporte prioritario")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Acciones
            Button(
                onClick = {
                    // Navegar al historial de compras
                    navController.navigate("catalogo_screen")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Historial de Compras")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {
                    // Cerrar sesión y volver al login
                    navController.navigate("login_screen") {
                        popUpTo("home_screen") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar Sesión", color = Color.Red)
            }
        }
    }
}

@Composable
fun InfoChip(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, color: Color) {
    Surface(
        color = color.copy(alpha = 0.2f),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text, color = color, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray)
        Text(value, color = Color.White)
    }
}

@Composable
fun BenefitItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text,
            color = Color.LightGray,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}