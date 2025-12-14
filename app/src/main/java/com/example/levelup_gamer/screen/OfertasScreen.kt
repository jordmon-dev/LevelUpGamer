package com.example.levelup_gamer.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.levelup_gamer.viewmodel.OfertasViewModel
import com.example.levelup_gamer.viewmodel.Oferta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfertasScreen(
    navController: NavController,
    viewModel: OfertasViewModel
) {
    // 1. Escuchamos el estado del ViewModel (Datos reales de la API)
    val ofertas by viewModel.ofertasState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Ofertas Steam (API)", fontWeight = FontWeight.Bold, color = Color.White) },
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
        containerColor = Color(0xFF0A0A0A)
    ) { innerPadding ->

        if (isLoading) {
            Box(Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF00FF88))
            }
        } else if (ofertas.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No se encontraron ofertas", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
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

@Composable
fun TarjetaOfertaItem(oferta: Oferta) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {

            // --- NUEVO: IMAGEN DESDE LA API ---
            if (oferta.juego.imagenUrl.isNotEmpty()) {
                AsyncImage(
                    model = oferta.juego.imagenUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            // ----------------------------------

            Column(modifier = Modifier.weight(1f)) {
                // Título y Badge
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = oferta.juego.titulo,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 2,
                        modifier = Modifier.weight(1f)
                    )

                    Surface(
                        color = Color(0xFF00FF88),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = "-${oferta.descuento}%",
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }

                Text(
                    text = "Plataforma: ${oferta.juego.plataforma}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Precios
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "$ ${oferta.juego.precioOriginal}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        // textDecoration = TextDecoration.LineThrough
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "$ ${oferta.precioConDescuento}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00FF88)
                    )
                }
            }
        }
    }
}