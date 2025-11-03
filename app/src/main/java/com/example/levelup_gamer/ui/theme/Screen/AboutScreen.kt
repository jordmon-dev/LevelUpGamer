package com.example.levelup_gamer.ui.theme.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.AboutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun About(navController: NavController, viewModel: AboutViewModel) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Acerca de Level-Up Gamer") },
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo/Header
            Text(
                "🎮 LEVEL-UP GAMER 🎮",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E90FF)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Misión
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Misión",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF39FF14)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Proporcionar productos de alta calidad para gamers en todo Chile, " +
                                "ofreciendo una experiencia de compra única y personalizada, " +
                                "con un enfoque en la satisfacción del cliente y el crecimiento " +
                                "de la comunidad gamer.",
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Visión
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Visión",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF39FF14)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Ser la tienda online líder en productos para gamers en Chile, " +
                                "reconocida por su innovación, servicio al cliente excepcional, " +
                                "y un programa de fidelización basado en gamificación que " +
                                "recompense a nuestros clientes más fieles.",
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Contacto
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "📞 Contacto",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E90FF)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("📧 contacto@levelupgamer.cl", color = Color.White)
                    Text("🌐 www.levelupgamer.cl", color = Color.White)
                    Text("📱 +56 9 1234 5678", color = Color.White)
                }
            }
        }
    }
}