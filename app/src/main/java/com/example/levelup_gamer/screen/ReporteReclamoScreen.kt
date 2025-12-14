package com.example.levelup_gamer.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

    // Usar collectAsState() para observar los cambios
    val foto by reclamoViewModel.fotoUri.collectAsState()
    val descripcion by reclamoViewModel.descripcion.collectAsState()
    val lat by reclamoViewModel.latitud.collectAsState()
    val lon by reclamoViewModel.longitud.collectAsState()
    val formularioCompleto by reclamoViewModel.formularioCompleto.collectAsState()

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
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
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

            // SECCIÓN FOTO
            Text(
                "Foto del producto",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
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

                    // Overlay para el botón
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.7f)
                                    ),
                                    startY = 150f
                                )
                            )
                    )

                    // Botón para cambiar foto
                    OutlinedButton(
                        onClick = { navController.navigate("camaraReclamo") },
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFF1E1E2E),
                            contentColor = Color.White
                        ),
                        border = BorderStroke(1.dp, Color(0xFF00FF88))
                    ) {
                        Text("Cambiar foto")
                    }
                }
            } else {
                OutlinedButton(
                    onClick = { navController.navigate("camaraReclamo") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFF1E1E2E),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFF444466))
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.CameraAlt,
                            contentDescription = "Cámara",
                            tint = Color(0xFF00FF88),
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Tomar fotografía")
                    }
                }
            }

            // SECCIÓN DESCRIPCIÓN
            Text(
                "Descripción del problema",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { reclamoViewModel.actualizarDescripcion(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                placeholder = {
                    Text(
                        "Describe el problema con el producto...",
                        color = Color(0xFFA0A0A0)
                    )
                },
                shape = RoundedCornerShape(12.dp),
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

            // SECCIÓN UBICACIÓN
            Text(
                "Ubicación",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )

            if (lat != null && lon != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1E1E2E)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "📍 Ubicación obtenida",
                            color = Color(0xFF00FF88),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Latitud: ${"%.6f".format(lat)}", color = Color.White)
                        Text("Longitud: ${"%.6f".format(lon)}", color = Color.White)
                    }
                }

                OutlinedButton(
                    onClick = { navController.navigate("gps") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFF1E1E2E),
                        contentColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color(0xFF444466))
                ) {
                    Text("Actualizar ubicación")
                }
            } else {
                OutlinedButton(
                    onClick = { navController.navigate("gps") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFF1E1E2E),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFF444466))
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = "Ubicación",
                            tint = Color(0xFF00FF88),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Obtener ubicación actual")
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // BOTÓN ENVIAR RECLAMO
            Button(
                onClick = {
                    if (reclamoViewModel.enviarReclamo()) {
                        navController.navigate("confirmacionReclamo")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = formularioCompleto,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (formularioCompleto) Color(0xFF00FF88) else Color(0xFF444466),
                    contentColor = if (formularioCompleto) Color.Black else Color(0xFF888888)
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