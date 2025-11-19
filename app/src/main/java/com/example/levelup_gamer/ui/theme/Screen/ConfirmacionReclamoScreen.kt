package com.example.levelup_gamer.ui.theme.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ConfirmacionReclamoScreen(navController: NavController) {

    val fondo = Brush.verticalGradient(
        listOf(
            Color(0xFF0A0A0A),
            Color(0xFF111122),
            Color(0xFF1A1A2E),
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1E1E2F).copy(alpha = 0.95f)
            ),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(26.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF00E676),
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Reclamo enviado con éxito",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "Hemos recibido tu solicitud. Un ejecutivo revisará tu caso y te contactará si es necesario.",
                    color = Color.LightGray,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    onClick = {
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                ) {
                    Text("Volver al inicio")
                }
            }
        }
    }
}


