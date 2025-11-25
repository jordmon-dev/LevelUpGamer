package com.example.levelup_gamer.ui.theme.Screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.ReclamoViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CamaraReclamoScreen(
    navController: NavController,
    reclamoViewModel: ReclamoViewModel
) {
    val context = LocalContext.current
    var tienePermiso by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var uriFoto by remember { mutableStateOf<Uri?>(null) }

    // Declarar el launcher de la cámara PRIMERO
    val launcherCamara = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            // La foto se guardó exitosamente
            uriFoto?.let { uri ->
                reclamoViewModel.actualizarFoto(uri)
            }
            navController.popBackStack()
        } else {
            // El usuario canceló o hubo error
            navController.popBackStack()
        }
    }

    val launcherPermisos = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        tienePermiso = concedido
        if (concedido) {
            // Crear archivo y lanzar cámara
            val uri = crearArchivoImagen(context)
            uri?.let {
                uriFoto = it
                launcherCamara.launch(it)
            }
        }
    }

    // Función para lanzar la cámara
    fun lanzarCamara() {
        val uri = crearArchivoImagen(context)
        uri?.let {
            uriFoto = it
            launcherCamara.launch(it)
        }
    }

    // Gradiente consistente
    val fondo = Brush.verticalGradient(
        listOf(
            Color(0xFF0A0A0A),
            Color(0xFF1A1A2E),
            Color(0xFF16213E)
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Tomar Foto",
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
                    containerColor = Color(0xFF0A0A0A)
                )
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(fondo)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (tienePermiso) {
                    // Mostrar instrucciones cuando se tienen permisos
                    Text(
                        "Listo para tomar foto",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        "La cámara se abrirá automáticamente.\n\nAsegúrate de que el producto esté bien iluminado y se vea claramente en la foto.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFFCCCCCC),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    CircularProgressIndicator(
                        color = Color(0xFF00FF88)
                    )

                    // Lanzar cámara automáticamente cuando se tengan los permisos
                    LaunchedEffect(Unit) {
                        lanzarCamara()
                    }
                } else {
                    // Solicitar permisos
                    Text(
                        "Permiso de Cámara",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        "Para tomar fotos del producto, necesitamos acceso a la cámara de tu dispositivo.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFFCCCCCC),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    Button(
                        onClick = {
                            launcherPermisos.launch(Manifest.permission.CAMERA)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00FF88),
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            "Conceder Permiso",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Cancelar",
                            color = Color(0xFF888888)
                        )
                    }
                }
            }
        }
    }
}

// Función para crear el archivo de imagen
private fun crearArchivoImagen(context: Context): Uri? {
    return try {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val photoFile = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )

        FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            photoFile
        )
    } catch (ex: Exception) {
        ex.printStackTrace()
        null
    }
}