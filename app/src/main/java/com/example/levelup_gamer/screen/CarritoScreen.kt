package com.example.levelup_gamer.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.levelup_gamer.R
import com.example.levelup_gamer.model.CarritoItem
import com.example.levelup_gamer.viewmodel.CarritoViewModel
import com.example.levelup_gamer.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel,
    productoViewModel: ProductoViewModel // Opcional si lo usas para algo más
) {
    // 1. Observamos el estado CORRECTO del ViewModel (File 1)
    val uiState by carritoViewModel.uiState.collectAsState()

    val brush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0A0A0A), Color(0xFF1A1A2E), Color(0xFF16213E))
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mi Carrito", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { carritoViewModel.vaciarCarrito() }) {
                        Icon(Icons.Default.Delete, "Vaciar", tint = Color(0xFFFF4444))
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent,
        bottomBar = {
            if (uiState.items.isNotEmpty()) {
                ResumenCompra(
                    total = uiState.total,
                    onPagar = { navController.navigate("pago") }
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
                .padding(padding)
        ) {
            if (uiState.items.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            null,
                            tint = Color.Gray,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Tu carrito está vacío", color = Color.Gray, fontSize = 18.sp)
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.items) { item ->
                        CarritoItemCard(
                            item = item,
                            onEliminar = {
                                // Convertimos a Int porque tu función eliminar pide Int
                                carritoViewModel.eliminarProducto(item.producto.id.toInt())
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CarritoItemCard(item: CarritoItem, onEliminar: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // --- IMAGEN (Ahora usa AsyncImage para URLs) ---
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.producto.imagen) // URL
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                placeholder = painterResource(R.drawable.game_1), // Imagen temporal
                error = painterResource(R.drawable.game_1),       // Imagen si falla
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.producto.nombre,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "$ ${item.producto.precio}",
                    color = Color(0xFF00FF88),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Cantidad: ${item.cantidad}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }

            IconButton(onClick = onEliminar) {
                Icon(Icons.Default.Delete, "Eliminar", tint = Color(0xFFFF4444))
            }
        }
    }
}

@Composable
fun ResumenCompra(total: Double, onPagar: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total a pagar:", color = Color.White, fontSize = 18.sp)
                Text(
                    "$ ${total.toInt()}", // Mostramos como entero para que se vea limpio
                    color = Color(0xFF00FF88),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onPagar,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00FF88),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Ir a Pagar", fontWeight = FontWeight.Bold)
            }
        }
    }
}