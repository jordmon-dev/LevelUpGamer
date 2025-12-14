package com.example.levelup_gamer.screen

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.example.levelup_gamer.viewmodel.ReclamoViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CamaraCapturaScreen(
    navController: NavController,
    reclamoViewModel: ReclamoViewModel
) {
    val contexto = LocalContext.current
    var fotoUri by remember { mutableStateOf<Uri?>(null) }

    // Función para crear archivo
    fun crearArchivo(context: Context): Uri {
        val carpeta = context.getExternalFilesDir(null)

        val archivo = File(
            carpeta,
            "foto_reclamo_${System.currentTimeMillis()}.jpg"
        )

        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",   // ✅ CORRECCIÓN: Autoridad corregida
            archivo
        )
    }


    // Launcher de cámara
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            reclamoViewModel.actualizarFoto(fotoUri)
        }
    }

    // Observar la foto guardada en el ViewModel
    val fotoGuardada by reclamoViewModel.fotoUri.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Capturar Foto", color = Color.White) },
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
                    containerColor = Color(0xFF111111)
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFF0D0D0D)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Mostrar imagen
            if (fotoGuardada != null) {
                Image(
                    painter = rememberAsyncImagePainter(fotoGuardada),
                    contentDescription = "Foto del reclamo",
                    modifier = Modifier
                        .size(280.dp)
                        .padding(8.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Botón para confirmar y volver
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF39FF14)),
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Usar esta foto")
                }
            } else {
                // Placeholder cuando no hay foto
                Box(
                    modifier = Modifier
                        .size(280.dp)
                        .background(Color(0xFF1E1E1E), RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "No hay foto tomada",
                        color = Color(0xFF888888)
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Botón para tomar foto
            Button(
                onClick = {
                    val nuevaUri = crearArchivo(contexto)
                    fotoUri = nuevaUri
                    launcher.launch(nuevaUri)
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00E5FF)),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Tomar Foto")
            }
        }
    }
}