package com.example.levelup_gamer.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.levelup_gamer.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {
    val usuarioState by usuarioViewModel.usuarioState.collectAsState()
    val fotoPerfil by usuarioViewModel.fotoPerfil.collectAsState()
    val isLoading by usuarioViewModel.isLoading.collectAsState()

    // 📸 Launcher para abrir la galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { usuarioViewModel.actualizarFotoPerfil(it.toString()) }
    }

    // 🔥 Lógica de Roles (CORREGIDA)
    // Ahora tomamos el dato real que viene de la Base de Datos
    // Si es nulo, asumimos "CLIENTE" por defecto.
    val rolActual = usuarioState.rol ?: "CLIENTE"

    LaunchedEffect(Unit) {
        if (usuarioState.email.isNotEmpty()) {
            usuarioViewModel.cargarPerfil(usuarioState.email)
        }
    }

    val nivelNombre = determinarNivel(usuarioState.puntosLevelUp)
    val progresoNivel = (usuarioState.puntosLevelUp % 500) / 500f

    val brush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0A0A0A), Color(0xFF1A1A2E), Color(0xFF16213E))
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mi Perfil", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
                .padding(padding)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF00FF88)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // --- SECCIÓN 1: CABECERA ---
                    Box(
                        modifier = Modifier.size(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(110.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF2A2A3E))
                                .border(2.dp, Color(0xFF00FF88), CircleShape)
                                .clickable { galleryLauncher.launch("image/*") },
                            contentAlignment = Alignment.Center
                        ) {
                            if (fotoPerfil != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(fotoPerfil),
                                    contentDescription = "Foto de perfil",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    modifier = Modifier.size(70.dp),
                                    tint = Color(0xFF00FF88)
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .offset(x = (-4).dp, y = (-4).dp)
                                .size(32.dp)
                                .background(Color(0xFF00FF88), CircleShape)
                                .border(2.dp, Color.Black, CircleShape)
                                .clickable { galleryLauncher.launch("image/*") },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Edit, null, tint = Color.Black, modifier = Modifier.size(18.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = usuarioState.nombre.ifEmpty { "Gamer Desconocido" },
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    // 🏷️ Badge de Rol
                    Surface(
                        color = when (rolActual) {
                            "ADMIN" -> Color(0xFFFFD700)
                            "SOPORTE" -> Color(0xFF00BFFF)
                            "INVITADO" -> Color.Gray
                            else -> Color(0xFF00FF88)
                        },
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Text(
                            text = rolActual,
                            color = Color.Black,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }

                    Text(
                        text = usuarioState.email,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // --- SECCIÓN 2: TARJETA DE NIVEL ---
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Rango Actual", color = Color.Gray, fontSize = 12.sp)
                                    Text(
                                        text = nivelNombre,
                                        color = Color(0xFF00FF88),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 22.sp
                                    )
                                }
                                Surface(
                                    color = Color(0xFF00FF88).copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        "${usuarioState.puntosLevelUp} XP",
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        color = Color(0xFF00FF88),
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            LinearProgressIndicator(
                                progress = { progresoNivel },
                                modifier = Modifier.fillMaxWidth().height(6.dp),
                                color = Color(0xFF00FF88),
                                trackColor = Color(0xFF2A2A3E),
                            )
                            Text("Próximo nivel en ${500 - (usuarioState.puntosLevelUp % 500)} XP", color = Color.Gray, fontSize = 10.sp, modifier = Modifier.padding(top=4.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // --- SECCIÓN 3: MIS PEDIDOS ---
                    if (rolActual == "CLIENTE" || rolActual == "ADMIN") {
                        Text(
                            text = "Mis Pedidos Recientes",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
                        )

                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            PedidoItem("PED-001", "20/12/2025", "$ 45.990", "Entregado", Color(0xFF00FF88))
                            PedidoItem("PED-002", "22/12/2025", "$ 12.990", "En Camino", Color(0xFFFFD700))
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    // --- SECCIÓN 4: CONFIGURACIÓN POR ROLES ---
                    Text(
                        text = "Configuración",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                        // 1. ROL ADMIN
                        if (rolActual == "ADMIN") {
                            OpcionPerfilNeon(
                                texto = "Panel de Administrador (CRUD)",
                                icono = Icons.Default.AdminPanelSettings,
                                color = Color(0xFFFFD700)
                            ) { navController.navigate("admin_agregar_producto") }

                            OpcionPerfilNeon(
                                texto = "Ver Ventas Globales",
                                icono = Icons.Default.AttachMoney, // O Icons.Default.List
                                color = Color(0xFF00FF88)
                            ) { navController.navigate("admin_ordenes") }
                        }

                        // 2. TODOS (Menos invitado)
                        if (rolActual != "INVITADO") {
                            OpcionPerfilNeon("Notificaciones", Icons.Default.Notifications) { navController.navigate("notificaciones") }
                        }

                        if (rolActual != "INVITADO") {
                            OpcionPerfilNeon("Notificaciones", Icons.Default.Notifications) { navController.navigate("notificaciones") }

                            // 👇 PEGA ESTO AQUÍ JUSTO DEBAJO DE NOTIFICACIONES 👇

                            // ✅ NUEVO BOTÓN WISHLIST
                            OpcionPerfilNeon(
                                texto = "Mi Wishlist (Favoritos)",
                                icono = Icons.Default.Favorite,
                                color = Color(0xFFFF4444) // Rojo corazón
                            ) { navController.navigate("favoritos") }
                        }


                        // 3. SOPORTE, ADMIN y CLIENTE (Reclamos)
                        if (rolActual != "INVITADO") {
                            OpcionPerfilNeon(
                                texto = if(rolActual == "SOPORTE") "Gestionar Reclamos (Cámara)" else "Ingresar Reclamo (Cámara)",
                                icono = Icons.Default.CameraAlt
                            ) { navController.navigate("reporteReclamo") }
                        }

                        // 4. CLIENTE y ADMIN (GPS)
                        if (rolActual == "CLIENTE" || rolActual == "ADMIN") {
                            OpcionPerfilNeon("Mi Ubicación (GPS)", Icons.Default.LocationOn) { navController.navigate("gps") }
                        }

                        // 5. ROL INVITADO
                        if (rolActual == "INVITADO") {
                            OpcionPerfilNeon(
                                texto = "Regístrate para comprar",
                                icono = Icons.Default.PersonAdd,
                                color = Color(0xFF00BFFF)
                            ) { navController.navigate("registro") }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Botón Cerrar Sesión
                    OutlinedButton(
                        onClick = {
                            usuarioViewModel.cerrarSesion()
                            navController.navigate("login") { popUpTo(0) { inclusive = true } }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFF4444)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFF4444))
                    ) {
                        Icon(Icons.Default.ExitToApp, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Cerrar Sesión")
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}

// -------------------------------------------------------------
// Componentes Auxiliares
// -------------------------------------------------------------

@Composable
fun PedidoItem(id: String, fecha: String, total: String, estado: String, colorEstado: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A3E)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Orden #$id", color = Color.White, fontWeight = FontWeight.Bold)
                Text(fecha, color = Color.Gray, fontSize = 12.sp)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(total, color = Color(0xFF00FF88), fontWeight = FontWeight.Bold)
                Text(estado, color = colorEstado, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun OpcionPerfilNeon(
    texto: String,
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color = Color.White,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icono, null, tint = Color(0xFF00FF88), modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(texto, color = color, modifier = Modifier.weight(1f))
            Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.Gray, modifier = Modifier.size(16.dp).rotate(180f))
        }
    }
}

fun determinarNivel(puntos: Int): String {
    return when {
        puntos >= 2000 -> "Leyenda"
        puntos >= 1000 -> "Diamante"
        puntos >= 500 -> "Oro"
        else -> "Principiante"
    }
}

fun Modifier.rotate(degrees: Float) = this.then(Modifier.graphicsLayer(rotationZ = degrees))