package com.example.levelup_gamer.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
// IMPORTS CRÍTICOS QUE FALTABAN:
import com.example.levelup_gamer.viewmodel.OfertasViewModel
import com.example.levelup_gamer.viewmodel.Oferta
import com.example.levelup_gamer.viewmodel.Juego

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfertasScreen(
    navController: NavController,
    viewModel: OfertasViewModel
) {
    // Obtenemos los datos
    val ofertas = remember { viewModel.obtenerOfertasLocales() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Ofertas Especiales", fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF0A0A0A)
                )
            )
        },
        containerColor = Color(0xFF0A0A0A) // Fondo oscuro general
    ) { innerPadding ->

        if (ofertas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.LocalOffer, null, modifier = Modifier.size(64.dp), tint = Color.Gray)
                    Text("No hay ofertas por ahora", color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(ofertas) { oferta ->
                    TarjetaOfertaItem(oferta)
                }
            }
        }
    }
}

// Componente extraído para mantener el código limpio
@Composable
fun TarjetaOfertaItem(oferta: Oferta) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Título y Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = oferta.juego.titulo,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "${oferta.juego.genero} • ${oferta.juego.plataforma}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                // Etiqueta de Descuento
                Surface(
                    color = Color(0xFF00FF88),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = "-${oferta.descuento}%",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Precios
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Precio Original tachado (podrías agregar estilo tachado si quieres)
                Text(
                    text = "$ ${oferta.juego.precioOriginal.toInt()}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    // textDecoration = TextDecoration.LineThrough // Opcional
                )

                Spacer(modifier = Modifier.width(12.dp))

                // Precio Final
                Text(
                    text = "$ ${oferta.precioConDescuento.toInt()}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00FF88)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Ahorro y Tiempo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Ahorras: $ ${oferta.ahorro.toInt()}",
                    color = Color(0xFF00E5FF),
                    style = MaterialTheme.typography.labelMedium
                )

                if (oferta.tiempoRestante.isNotEmpty()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocalOffer, null, tint = Color(0xFFFF6B6B), modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Quedan: ${oferta.tiempoRestante}",
                            color = Color(0xFFFF6B6B),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}