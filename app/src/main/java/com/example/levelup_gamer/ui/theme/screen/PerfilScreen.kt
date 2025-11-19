package com.example.levelup_gamer.ui.theme.screen

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelup_gamer.model.UsuarioPerfil
import com.example.levelup_gamer.viewmodel.UsuarioViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Perfil(navController: NavController, viewModel: UsuarioViewModel) {
    val uiState by viewModel.usuario.collectAsState()

    // Crear el objeto UsuarioPerfil con los datos del ViewModel
    val usuario = remember(uiState) {
        UsuarioPerfil(
            nombre = uiState.nombre,
            email = uiState.correo,
            puntosLevelUp = 1500, // Puedes mantener esto como valor por defecto o agregarlo al ViewModel
            nivel = if (uiState.correo.endsWith("@duocuc.cl")) "Gamer Pro Estudiante" else "Gamer",
            fechaRegistro = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date()) // Fecha actual
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
                    IconButton(onClick = {
                        // Navegar a pantalla de edición o abrir diálogo
                    }) {
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

                    // Mostrar edad si está disponible
                    if (uiState.edad.isNotEmpty()) {
                        Text(
                            "${uiState.edad} años",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.LightGray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

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

                    // Mostrar descuento especial para estudiantes Duoc
                    val descuento = if (uiState.correo.endsWith("@duocuc.cl")) {
                        "20% (Estudiante Duoc)"
                    } else {
                        "10% (Usuario Regular)"
                    }
                    InfoRow("Descuento:", descuento)

                    // Mostrar edad si está disponible
                    if (uiState.edad.isNotEmpty()) {
                        InfoRow("Edad:", "${uiState.edad} años")
                    }
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

                    // Beneficios dinámicos basados en el tipo de usuario
                    if (uiState.correo.endsWith("@duocuc.cl")) {
                        BenefitItem("✓ Descuento exclusivo 20% para estudiantes Duoc")
                    } else {
                        BenefitItem("✓ Descuento regular 10% para usuarios")
                    }
                    BenefitItem("✓ Acumulación de puntos por compras")
                    BenefitItem("✓ Acceso a ofertas especiales")
                    BenefitItem("✓ Soporte prioritario")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Acciones
            Button(
                onClick = {
                    // Navegar al catálogo
                    navController.navigate("catalogo")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver Catálogo de Productos")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    // Navegar al historial de compras (si existe esa pantalla)
                    navController.navigate("historial")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E90FF)
                )
            ) {
                Text("Ver Historial de Compras")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {
                    // Cerrar sesión y volver al login
                    viewModel.limpiarEstado() // Limpiar los datos del usuario
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
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
fun InfoChip(icon: ImageVector, text: String, color: Color) {
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