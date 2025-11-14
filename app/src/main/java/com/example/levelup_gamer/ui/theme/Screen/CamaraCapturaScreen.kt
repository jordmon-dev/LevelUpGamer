package com.example.levelup_gamer.ui.theme.Screen

import android.Manifest
//import android.graphics.Color
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CamaraCaptura(navController: NavController) {
    val contexto = LocalContext.current

    var permisoCamara by remember { mutableStateOf(false) }
    var capturaImagenUri by remember { mutableStateOf<Uri?>(null) }

    // Crear archivo temporal para guardar la imagen
    val fotoFile: File = remember {
        val tiempo = System.currentTimeMillis()
        File.createTempFile(
            "Foto_${tiempo}",
            ".jpg",
            contexto.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
    }

    val uri = remember {
        FileProvider.getUriForFile(
            contexto,
            "${contexto.packageName}.fileprovider",
            fotoFile
        )
    }

    // Solicitar permiso de cámara
    val pedirPermisoCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permisoCamara = isGranted
    }

    // Lanzar cámara
    val lanzarCamara = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            capturaImagenUri = uri
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Cámara de Captura") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1A1A1A),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            capturaImagenUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(model = it),
                    contentDescription = "Foto capturada",
                    modifier = Modifier
                        .size(250.dp)
                        .padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (!permisoCamara) {
                        pedirPermisoCamara.launch(Manifest.permission.CAMERA)
                    } else {
                        lanzarCamara.launch(uri)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E90FF))
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = "Tomar foto")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Tomar Foto")
            }
        }
    }
}


