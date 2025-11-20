package com.example.levelup_gamer.ui.theme.Screen

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.camera.view.PreviewView
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.CameraViewModel
import com.example.levelup_gamer.viewmodel.ReclamoViewModel
import java.util.concurrent.Executors

@Composable
fun CamaraReclamoScreen(
    navController: NavController,
    reclamoViewModel: ReclamoViewModel = viewModel()
) {
    val cameraViewModel: CameraViewModel = viewModel()
    val context = LocalContext.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    var isPreviewReady by remember { mutableStateOf(false) }

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
                    cameraViewModel.closeCamera()
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
                text = "Tomar foto del producto",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.size(48.dp))
        }

        // Vista de la cámara
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            AndroidView(
                factory = { context ->
                    PreviewView(context).apply {
                        scaleType = PreviewView.ScaleType.FILL_CENTER
                        post {
                            cameraViewModel.startCamera(context, this, cameraExecutor)
                            isPreviewReady = true
                        }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            if (!isPreviewReady) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = Color.White)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Inicializando cámara...",
                        color = Color.White
                    )
                }
            }
        }

        // Botón para tomar foto
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            val isCameraReady = cameraViewModel.isCameraInitialized

            FloatingActionButton(
                onClick = {
                    if (isCameraReady) {
                        cameraViewModel.takePhoto(context, cameraExecutor) { uri ->
                            // Guardar la foto en el ViewModel de reclamos
                            reclamoViewModel.actualizarFoto(uri)
                            Log.d("CamaraReclamo", "Foto tomada: $uri")
                            cameraViewModel.closeCamera()
                            navController.popBackStack()
                        }
                    }
                },
                containerColor = if (isCameraReady) Color.White else Color.Gray,
                contentColor = if (isCameraReady) Color.Black else Color.White,
                modifier = Modifier.size(70.dp)
            ) {
                if (isCameraReady) {
                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = "Tomar foto",
                        modifier = Modifier.size(35.dp)
                    )
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.size(25.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                }
            }
        }
    }

    // Manejar errores
    cameraViewModel.cameraError?.let { error ->
        LaunchedEffect(error) {
            Log.e("CamaraReclamo", "Error de cámara: $error")
            // Podrías mostrar un Snackbar con el error
        }
    }

    // Cleanup
    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
            cameraViewModel.closeCamera()
        }
    }
}