package com.example.levelup_gamer.ui.theme.Screen

import android.Manifest
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.levelup_gamer.viewmodel.ReclamoViewModel
import java.io.File
import java.util.concurrent.Executors

@Composable
fun CamaraReclamoScreen(
    navController: NavController,
    reclamoViewModel: ReclamoViewModel = viewModel()
) {
    val contexto = LocalContext.current
    val fotoUri by reclamoViewModel.fotoUri.collectAsState()
    val permisoCamara by reclamoViewModel.permisoCamara.collectAsState()

    // Preparar cámara al iniciar
    LaunchedEffect(Unit) {
        reclamoViewModel.prepararCamara(contexto)
    }

    // Lanzar cámara
    val lanzarCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        reclamoViewModel.manejarFotoTomada(success)
    }

    // Pedir permisos
    val pedirPermisosCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        reclamoViewModel.actualizarPermisoCamara(isGranted)
        if (isGranted) {
            reclamoViewModel.getUriParaCamara()?.let { uri ->
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
                }
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Cerrar cámara",
                    tint = Color.White
                )
            }

            Text(
                text = "Tomar foto del producto",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.size(48.dp))
        }

        // Vista previa
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.DarkGray)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (fotoUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(model = fotoUri),
                    contentDescription = "Foto del reclamo",
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = "Cámara",
                        tint = Color.White,
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Presiona el botón para tomar una foto",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // Botones
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    if (!permisoCamara) {
                        pedirPermisosCamara.launch(Manifest.permission.CAMERA)
                    } else {
                        reclamoViewModel.getUriParaCamara()?.let { uri ->
                            lanzarCamara.launch(uri)
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = "Tomar foto")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Tomar Foto")
            }

            Spacer(modifier = Modifier.width(16.dp))

            if (fotoUri != null) {
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Usar Foto")
                }
            }
        }
    }
}