package com.example.levelup_gamer.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificacionScreen(navController: NavController) {
    val contexto = LocalContext.current
    var listaNotificaciones by remember { mutableStateOf(obtenerNotificacionesIniciales().toMutableList()) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Notificaciones") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val nueva = "📢 Nueva promoción especial #${listaNotificaciones.size + 1}"
                listaNotificaciones.add(0, nueva)

                // Mostrar mensaje Toast al usuario
                Toast.makeText(contexto, "Nueva notificación agregada", Toast.LENGTH_SHORT).show()
            }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar notificación")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            LazyListScope.items(listaNotificaciones) { noti ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(noti, style = MaterialTheme.typography.bodyLarge)
                        Text("Hace unos minutos", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

// Lista de notificaciones por defecto
fun obtenerNotificacionesIniciales(): List<String> = listOf(
    "🔥 10% de descuento en teclados gamer",
    "🎮 Nuevo producto: consola portátil Neo X",
    "⭐ Participa en nuestro torneo semanal",
    "💬 Soporte técnico respondió tu solicitud"
)



