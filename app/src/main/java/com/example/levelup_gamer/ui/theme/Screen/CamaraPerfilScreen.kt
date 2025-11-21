// CamaraPerfilScreen.kt
package com.example.levelup_gamer.ui.theme.Screen

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.levelup_gamer.viewmodel.UsuarioViewModel
import kotlinx.coroutines.delay

@Composable
fun CamaraPerfilScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel = viewModel()
) {
    val contexto = LocalContext.current
    val fotoPerfilUri by usuarioViewModel.fotoPerfilUri.collectAsState()
    val permisoCamara by usuarioViewModel.permisoCamara.collectAsState()

    // Estado local para controlar si ya se preparó la cámara
    var camaraPreparada by remember { mutableStateOf(false) }

    // Estado para controlar la navegación después de tomar foto
    var shouldNavigateBack by remember { mutableStateOf(false) }

    // Preparar cámara al iniciar (solo una vez)
    LaunchedEffect(Unit) {
        if (!camaraPreparada) {
            usuarioViewModel.prepararCamara(contexto)
            camaraPreparada = true
        }
    }

    // Navegar de regreso cuando se complete la foto
    LaunchedEffect(shouldNavigateBack) {
        if (shouldNavigateBack) {
            delay(1000) // Pequeña espera para que el usuario vea la foto
            navController.popBackStack()
            shouldNavigateBack = false
        }
    }

    // Lanzar cámara
    val lanzarCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            usuarioViewModel.manejarFotoTomada(success)
            shouldNavigateBack = true
        }
    }

    // Pedir permisos
    val pedirPermisosCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        usuarioViewModel.actualizarPermisoCamara(isGranted)
        if (isGranted) {
            usuarioViewModel.getUriParaCamara()?.let { uri ->
                lanzarCamara.launch(uri)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Cerrar cámara",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }

            Text(
                text = "Foto de perfil",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.size(48.dp))
        }

        // Vista previa - Diseño circular para perfil
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            if (fotoPerfilUri != null) {
                // Mostrar imagen en forma circular
                Box(
                    modifier = Modifier
                        .size(300.dp)
                        .background(Color.DarkGray, CircleShape)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = fotoPerfilUri),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }

                // Mensaje de éxito
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    Text(
                        text = "¡Foto tomada con éxito!",
                        color = Color(0xFF00FF88),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "Regresando automáticamente...",
                        color = Color(0xFFB0BEC5),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            } else {
                // Placeholder circular
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(300.dp)
                            .background(Color.DarkGray, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.CameraAlt,
                            contentDescription = "Cámara",
                            tint = Color.White,
                            modifier = Modifier.size(80.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        "Toma una foto para tu perfil",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        // Botones
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botón para tomar foto
            Button(
                onClick = {
                    if (!permisoCamara) {
                        pedirPermisosCamara.launch(Manifest.permission.CAMERA)
                    } else {
                        usuarioViewModel.getUriParaCamara()?.let { uri ->
                            lanzarCamara.launch(uri)
                        } ?: run {
                            // Si no hay URI, preparar la cámara primero
                            usuarioViewModel.prepararCamara(contexto)
                            usuarioViewModel.getUriParaCamara()?.let { uri ->
                                lanzarCamara.launch(uri)
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                modifier = Modifier.weight(1f),
                enabled = fotoPerfilUri == null && !shouldNavigateBack
            ) {
                Icon(
                    Icons.Default.CameraAlt,
                    contentDescription = "Tomar foto",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    when {
                        shouldNavigateBack -> "Guardando..."
                        fotoPerfilUri != null -> "Foto Tomada"
                        else -> "Tomar Foto"
                    }
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Botón para regresar
            Button(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF666666),
                    contentColor = Color.White
                ),
                enabled = !shouldNavigateBack
            ) {
                Text("Volver")
            }
        }
    }
}