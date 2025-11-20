// ReporteReclamoScreen.kt - VERSIÓN CORREGIDA
package com.example.levelup_gamer.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.levelup_gamer.viewmodel.ReclamoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReporteReclamoScreen(
    navController: NavController,
    reclamoViewModel: ReclamoViewModel
) {

    val foto = reclamoViewModel.fotoUri.value
    val descripcion = reclamoViewModel.descripcion.value
    val lat = reclamoViewModel.latitud.value
    val lon = reclamoViewModel.longitud.value

    // Gradiente consistente con el resto de la app
    val fondo = Brush.verticalGradient(
        listOf(
            Color(0xFF0A0A0A),
            Color(0xFF1A1A2E),
            Color(0xFF16213E)
        )
    )

    // Color para la top bar
    val topBarColor = Color(0xFF0A0A0A)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Reporte de Reclamo",
                        color = Color.White
                    )
                },
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
                    containerColor = topBarColor
                )
            )
        },
        containerColor = Color.Transparent
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(fondo)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // FOTO
            Text(
                "Foto del producto",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )

            if (foto != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = foto),
                        contentDescription = "Foto del producto",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    )

                    // Botón para cambiar foto
                    OutlinedButton(
                        onClick = { navController.navigate("camaraReclamo") },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFF1E1E2E),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Cambiar foto")
                    }
                }
            } else {
                OutlinedButton(
                    onClick = { navController.navigate("camaraReclamo") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFF1E1E2E),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Tomar fotografía")
                }
            }

            Spacer(Modifier.height(10.dp))

            // DESCRIPCIÓN
            Text(
                "Descripción del problema",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { reclamoViewModel.actualizarDescripcion(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                minLines = 4,
                maxLines = 6,
                placeholder = {
                    Text(
                        "Describe el problema con el producto...",
                        color = Color(0xFFA0A0A0)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00FF88),
                    unfocusedBorderColor = Color(0xFF444466),
                    cursorColor = Color(0xFF00FF88),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFF00FF88),
                    unfocusedLabelColor = Color(0xFFA0A0A0)
                )
            )

            // UBICACIÓN
            Text(
                "Ubicación",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )

            if (lat != null && lon != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1E1E2E)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "Ubicación obtenida:",
                            color = Color(0xFF00FF88),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text("Latitud: $lat", color = Color.White)
                        Text("Longitud: $lon", color = Color.White)
                    }
                }

                OutlinedButton(
                    onClick = { navController.navigate("gps") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFF1E1E2E),
                        contentColor = Color.White
                    )
                ) {
                    Text("Actualizar ubicación")
                }
            } else {
                OutlinedButton(
                    onClick = { navController.navigate("gps") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFF1E1E2E),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Obtener ubicación actual")
                }
            }

            Spacer(Modifier.height(20.dp))

            // ENVIAR RECLAMO
            Button(
                onClick = {
                    // Aquí puedes agregar lógica para enviar el reclamo
                    navController.navigate("confirmacionReclamo")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = foto != null && descripcion.isNotBlank() && lat != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00FF88),
                    contentColor = Color.Black
                )
            ) {
                Text(
                    "Enviar Reclamo",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}