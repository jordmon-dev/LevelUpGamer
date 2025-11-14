package com.example.levelup_gamer.ui.theme.Screen

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAyuda(navController: NavController) {
    val contexto = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Centro de Ayuda") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("Preguntas Frecuentes", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text("• ¿Cómo recupero mi contraseña?")
            Text("  → Desde la pantalla de inicio de sesión selecciona ‘Olvidé mi contraseña’.")
            Spacer(modifier = Modifier.height(6.dp))
            Text("• ¿Cómo obtengo descuentos?")
            Text("  → Los correos con dominio @duocuc.cl reciben 20% automático.")
            Spacer(modifier = Modifier.height(6.dp))
            Text("• ¿Dónde puedo revisar mis pedidos?")
            Text("  → En la sección de 'Perfil' encontrarás tu historial.")
            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {
                try {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:soporte@levelupgamer.cl")
                        putExtra(Intent.EXTRA_SUBJECT, "Consulta desde Level-Up Gamer")
                    }
                    contexto.startActivity(intent)
                    Toast.makeText(contexto, "Abriendo correo de soporte...", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(contexto, "No se pudo abrir el correo", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("📧 Contactar Soporte Técnico")
            }
        }
    }
}
