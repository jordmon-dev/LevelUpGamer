// PerfilScreen.kt - VERSIÓN CORREGIDA
package com.example.levelup_gamer.ui.theme.Screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.levelup_gamer.viewmodel.CameraViewModel
import com.example.levelup_gamer.viewmodel.UsuarioViewModel
import java.util.concurrent.Executors

// Clase para manejar el estado de permisos
class CameraPermissionState(
    val hasPermission: Boolean,
    val onRequestPermission: () -> Unit
)

@Composable
fun rememberCameraPermissionState(): CameraPermissionState {
    val context = LocalContext.current
    var hasPermission by remember { mutableStateOf(checkCameraPermission(context)) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
        Log.d("CameraPermission", "Permiso de cámara concedido: $isGranted")
    }

    return remember(hasPermission) {
        CameraPermissionState(
            hasPermission = hasPermission,
            onRequestPermission = { launcher.launch(Manifest.permission.CAMERA) }
        )
    }
}

private fun checkCameraPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {
    val usuarioPerfil = usuarioViewModel.toUsuarioPerfil()
    val cameraViewModel: CameraViewModel = viewModel()
    val cameraPermissionState = rememberCameraPermissionState()

    // Estado para la foto de perfil
    var fotoPerfilUri by remember { mutableStateOf<String?>(null) }

    val fondo = Brush.verticalGradient(
        listOf(
            Color(0xFF050510),
            Color(0xFF0B1020),
            Color(0xFF11162B),
            Color(0xFF050510)
        )
    )

    val scrollState = rememberScrollState()

    // Manejar la lógica de apertura de cámara con permisos
    LaunchedEffect(cameraViewModel.showCamera) {
        if (cameraViewModel.showCamera && !cameraPermissionState.hasPermission) {
            cameraPermissionState.onRequestPermission()
        }
    }

    // Mostrar cámara si está activa y tenemos permisos
    if (cameraViewModel.showCamera && cameraPermissionState.hasPermission) {
        CameraScreen(
            cameraViewModel = cameraViewModel,
            onPhotoTaken = { uri ->
                fotoPerfilUri = uri
                usuarioViewModel.guardarFotoPerfil(uri)
            },
            onClose = {
                cameraViewModel.closeCamera()
            }
        )
        return
    }

    // Mostrar diálogo si no hay permisos pero se intentó abrir la cámara
    if (cameraViewModel.showCamera && !cameraPermissionState.hasPermission) {
        AlertDialog(
            onDismissRequest = { cameraViewModel.closeCamera() },
            title = { Text("Permiso de cámara requerido") },
            text = { Text("La aplicación necesita acceso a la cámara para tomar fotos de perfil.") },
            confirmButton = {
                Button(
                    onClick = { cameraPermissionState.onRequestPermission() }
                ) {
                    Text("Solicitar permiso")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { cameraViewModel.closeCamera() }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mi Perfil", color = Color.White) },
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
                    containerColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->

        Box(
            Modifier
                .fillMaxSize()
                .background(fondo)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(Modifier.height(16.dp))

                // ICONO / AVATAR - Ahora clickeable para cambiar foto
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .clickable {
                            cameraViewModel.openCamera()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    // Contenedor de la imagen
                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1B2338)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (fotoPerfilUri != null) {
                            // Mostrar la foto tomada
                            AsyncImage(
                                model = fotoPerfilUri,
                                contentDescription = "Foto de perfil",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Usuario",
                                tint = Color(0xFF00E5FF),
                                modifier = Modifier.size(60.dp)
                            )
                        }
                    }

                    // Badge de cámara (icono de cámara en esquina inferior derecha)
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(36.dp)
                            .background(Color(0xFF00E5FF), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.CameraAlt,
                            contentDescription = "Cambiar foto",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Texto indicador
                Text(
                    text = "Toca para cambiar foto",
                    color = Color(0xFFB0BEC5),
                    style = MaterialTheme.typography.labelSmall
                )

                Spacer(Modifier.height(18.dp))

                // NOMBRE
                Text(
                    text = usuarioPerfil.nombre,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                // CORREO
                Text(
                    text = usuarioPerfil.email,
                    color = Color(0xFFB0BEC5),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(26.dp))

                // CARD DE INFO
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(Color(0xFF151A2A)),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Text(
                            "Nivel: ${usuarioPerfil.nivel}",
                            color = Color(0xFF00E5FF),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            "Puntos Level-Up: ${usuarioPerfil.puntosLevelUp}",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            "Miembro desde: ${usuarioPerfil.fechaRegistro}",
                            color = Color(0xFFB0BEC5),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                Spacer(Modifier.height(32.dp))

                // OPCIONES
                Text(
                    text = "Opciones",
                    color = Color(0xFF00E5FF),
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(14.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    BotonOpcionPerfil("Ver Notificaciones") {
                        navController.navigate("notificaciones")
                    }

                    BotonOpcionPerfil("Reportar Reclamo") {
                        navController.navigate("reporteReclamo")
                    }

                    BotonOpcionPerfil("Configurar Dirección GPS") {
                        navController.navigate("gps")
                    }
                }

                Spacer(Modifier.height(32.dp))

                // BOTÓN CERRAR SESIÓN
                Button(
                    onClick = {
                        usuarioViewModel.cerrarSesion()
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE53935),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Cerrar sesión", fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun CameraScreen(
    cameraViewModel: CameraViewModel,
    onPhotoTaken: (String) -> Unit,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    // Estado para controlar si la cámara está lista
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
                onClick = onClose,
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
                text = "Tomar foto de perfil",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.size(48.dp))
        }

        // Vista de la cámara con indicador de carga
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

            // Mostrar mensaje de carga mientras la cámara se inicializa
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

        // Botón para tomar foto (solo habilitado cuando la cámara está lista)
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
                            onPhotoTaken(uri.toString())
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

    // Manejar errores de la cámara
    cameraViewModel.cameraError?.let { error ->
        LaunchedEffect(error) {
            Log.e("CameraScreen", "Error de cámara: $error")
        }
    }

    // Cleanup al desmontar el composable
    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }
}

@Composable
fun BotonOpcionPerfil(texto: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(Color(0xFF1B2335)),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = texto,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }
    }
}