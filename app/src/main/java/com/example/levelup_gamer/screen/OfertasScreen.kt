package com.example.levelup_gamer.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.levelup_gamer.viewmodel.OfertasViewModel

@Composable
fun OfertasScreen(navController: NavController, viewModel: OfertasViewModel) {
    // 1. Escuchamos al ViewModel
    val ofertas by viewModel.ofertasState.collectAsState()
    val loading by viewModel.isLoading.collectAsState()
    val error by viewModel.errorMessage.collectAsState()

    // 2. Dibujamos según el estado
    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (error != null) {
            Text("Error: $error", color = Color.Red, modifier = Modifier.align(Alignment.Center))
            Button(onClick = { viewModel.cargarOfertas() }, modifier = Modifier.align(Alignment.BottomCenter)) {
                Text("Reintentar")
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(ofertas) { oferta ->
                    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E))) {
                        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = oferta.juego.imagenUrl,
                                contentDescription = null,
                                modifier = Modifier.size(60.dp),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(oferta.juego.titulo, color = Color.White, style = MaterialTheme.typography.titleMedium)
                                Text("Antes: $${oferta.juego.precio}", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                                Text("Ahora: $${oferta.precioOferta}", color = Color(0xFF00FF88), style = MaterialTheme.typography.titleLarge)
                            }
                        }
                    }
                }
            }
        }
    }
}