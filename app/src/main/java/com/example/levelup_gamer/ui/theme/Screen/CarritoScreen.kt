package com.example.levelup_gamer.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelup_gamer.R
import com.example.levelup_gamer.viewmodel.CarritoViewModel
import com.example.levelup_gamer.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel,
    productoViewModel: ProductoViewModel // Opcional, si lo usas
) {
    val uiState by carritoViewModel.uiState.collectAsState()
    val brush = Brush.verticalGradient(listOf(Color(0xFF0A0A0A), Color(0xFF1A1A2E)))

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mi Carrito", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().background(brush).padding(padding)) {
            if (uiState.items.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("El carrito está vacío", color = Color.Gray)
                }
            } else {
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(uiState.items) { item ->
                            Card(
                                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E))
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = item.producto.imagen ?: R.drawable.game_1),
                                        contentDescription = null,
                                        modifier = Modifier.size(70.dp).padding(4.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(item.producto.nombre, color = Color.White, fontWeight = FontWeight.Bold)
                                        Text("Cant: ${item.cantidad}", color = Color.Gray)
                                        Text("$ ${item.producto.precio.toInt()}", color = Color(0xFF00FF88))
                                    }
                                    IconButton(
                                        onClick = {
                                            // IMPORTANTE: Eliminar usando el ID del producto
                                            item.producto.id?.let { id ->
                                                carritoViewModel.eliminarProducto(id)
                                            }
                                        }
                                    ) {
                                        Icon(Icons.Default.Delete, null, tint = Color(0xFFFF6B6B))
                                    }
                                }
                            }
                        }
                    }

                    // Resumen y Botón Pagar
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A3E))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Total:", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                Text("$ ${uiState.total.toInt()}", color = Color(0xFF00FF88), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { navController.navigate("pago") }, // Asegúrate que esta ruta exista
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF88), contentColor = Color.Black)
                            ) {
                                Text("Ir a Pagar", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}