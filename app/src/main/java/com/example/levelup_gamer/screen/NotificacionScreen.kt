package com.example.levelup_gamer.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificacionScreen(navController: NavController) {
    val contexto = LocalContext.current

    // Lista simulada de notificaciones
    val notificaciones = listOf(
        NotificacionData("¡Oferta Flash!", "Cyberpunk 2077 con 50% de descuento solo por hoy.", "Hace 10 min", true),
        NotificacionData("Pedido Enviado", "Tu orden #PED-002 ha salido de nuestras bodegas.", "Hace 2 horas", false),
        NotificacionData("Bienvenido", "Gracias por unirte a la comunidad Level-Up Gamer.", "Ayer", false)
    )

    val brush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0A0A0A), Color(0xFF1A1A2E), Color(0xFF16213E))
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Notificaciones", color = Color.White, fontWeight = FontWeight.Bold) },
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
        Box(modifier = Modifier.fillMaxSize().background(brush).padding(padding)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(notificaciones) { noti ->
                    NotificacionItem(noti)
                }
            }
        }
    }
}

data class NotificacionData(val titulo: String, val cuerpo: String, val tiempo: String, val esNueva: Boolean)

@Composable
fun NotificacionItem(data: NotificacionData) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Icon(Icons.Default.Notifications, null, tint = if(data.esNueva) Color(0xFF00FF88) else Color.Gray)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(data.titulo, color = Color.White, fontWeight = FontWeight.Bold)
                    Text(data.tiempo, color = Color.Gray, fontSize = 12.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(data.cuerpo, color = Color(0xFFCCCCCC), fontSize = 14.sp)
            }
        }
    }
}