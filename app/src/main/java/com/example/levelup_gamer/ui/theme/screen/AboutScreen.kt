package com.example.levelup_gamer.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController, viewModel: Any? = null) {

    val fondo = Brush.verticalGradient(
        listOf(
            Color(0xFF0A0A0A),
            Color(0xFF111122),
            Color(0xFF1A1A2E),
            Color(0xFF0D0D18)
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Sobre Nosotros", color = Color.White) },
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
        },
        containerColor = Color.Transparent
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(fondo)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 🔵 LOGO O TÍTULO
            Text(
                "LEVEL-UP GAMER",
                color = Color(0xFF00E5FF),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(20.dp))

            // 💠 MISIÓN
            TarjetaInfo(
                titulo = "Nuestra Misión",
                texto = """
                    Entregar a nuestra comunidad gamer productos de alta calidad, 
                    asesoría confiable y una experiencia de compra segura, ágil 
                    y con un enfoque centrado en las necesidades reales de cada jugador.
                """.trimIndent()
            )

            Spacer(Modifier.height(16.dp))

            // 💠 VISIÓN
            TarjetaInfo(
                titulo = "Nuestra Visión",
                texto = """
                    Convertirnos en la tienda gamer más confiable del país, 
                    expandiendo nuestra presencia y ofreciendo innovación, 
                    precios competitivos y un servicio postventa que marque la diferencia.
                """.trimIndent()
            )

            Spacer(Modifier.height(16.dp))

            // 💠 INFORMACIÓN EXTRA
            TarjetaInfo(
                titulo = "¿Qué nos hace únicos?",
                texto = """
                    • Soporte especializado en hardware y periféricos gamer  
                    • Catálogo actualizado cada semana  
                    • Beneficios para estudiantes DUOC  
                    • Envíos seguros a todo Chile  
                    • Sistema de puntos Level-Up para clientes frecuentes  
                """.trimIndent()
            )

            Spacer(Modifier.height(30.dp))

            Text(
                "Gracias por ser parte de la comunidad gamer.",
                color = Color.LightGray,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun TarjetaInfo(titulo: String, texto: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(Color(0xFF1A1A26)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            Text(
                titulo,
                color = Color(0xFF00E5FF),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                texto,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
