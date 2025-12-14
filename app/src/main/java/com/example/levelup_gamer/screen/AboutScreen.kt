package com.example.levelup_gamer.screen

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

    // Mismo gradiente que LoginScreen
    val fondo = Brush.verticalGradient(
        listOf(
            Color(0xFF0A0A0A),
            Color(0xFF1A1A2E),
            Color(0xFF16213E)
        )
    )

    // Color para la top bar que coincida con el fondo
    val topBarColor = Color(0xFF0A0A0A) // Usamos el color más oscuro del gradiente

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Sobre Nosotros",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                },
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
                    containerColor = topBarColor // Cambiar a color sólido que coincida
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

            // Título estilo Login
            Text(
                "LEVEL-UP GAMER",
                color = Color(0xFF00FF88),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(Modifier.height(20.dp))

            TarjetaInfo(
                titulo = "Nuestra Misión",
                texto = """
                    Entregar a nuestra comunidad gamer productos de alta calidad, 
                    asesoría confiable y una experiencia de compra segura, ágil 
                    y centrada en las necesidades reales de cada jugador.
                """.trimIndent()
            )

            Spacer(Modifier.height(16.dp))

            TarjetaInfo(
                titulo = "Nuestra Visión",
                texto = """
                    Convertirnos en la tienda gamer más confiable del país, 
                    expandiendo nuestra presencia y ofreciendo innovación, 
                    precios competitivos y un servicio postventa que marque la diferencia.
                """.trimIndent()
            )

            Spacer(Modifier.height(16.dp))

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
                color = Color(0xFFA0A0A0),
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
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E2E) // mismo estilo de tarjeta que login
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            Text(
                titulo,
                color = Color(0xFF00FF88),
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