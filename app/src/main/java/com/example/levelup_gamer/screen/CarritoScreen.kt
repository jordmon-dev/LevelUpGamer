package com.example.levelup_gamer.screen

import androidx.compose.foundation.Image
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

/*Otros import de imagenes
import com.example.levelup_gamer.R
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
* */




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

    // 1. LÓGICA INTELIGENTE (Misma que en Catálogo)
    // Detectamos el nombre para asignar la foto local
    val imagenLocal = when {
        item.producto.nombre.contains("Cyberpunk", ignoreCase = true) -> R.drawable.cyberpunk
        item.producto.nombre.contains("Witcher", ignoreCase = true) -> R.drawable.witcher3
        item.producto.nombre.contains("Red Dead", ignoreCase = true) -> R.drawable.reddead2
        item.producto.nombre.contains("Last of Us", ignoreCase = true) -> R.drawable.tlou2
        item.producto.nombre.contains("Elden Ring", ignoreCase = true) -> R.drawable.eldenring
        item.producto.nombre.contains("GTA", ignoreCase = true)
                || item.producto.nombre.contains ("Grand Theft", ignoreCase= true ) -> R.drawable.gta5
        item.producto.nombre.contains("PS5", ignoreCase = true) -> R.drawable.consola_ps5
        item.producto.nombre.contains("Xbox", ignoreCase = true) -> R.drawable.consola_xbox
        item.producto.nombre.contains("Switch", ignoreCase = true) -> R.drawable.consola_switch
        item.producto.nombre.contains("PC Gamer", ignoreCase = true) -> R.drawable.pc_gamer
        item.producto.nombre.contains("VR", ignoreCase = true) -> R.drawable.lentes_vr
        item.producto.nombre.contains("Phone", ignoreCase = true) -> R.drawable.celular_gamer
        else -> null
    }

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

            // 2. VISUALIZACIÓN DE IMAGEN
            if (imagenLocal != null) {
                // Si encontramos la foto local, la mostramos
                Image(
                    painter = painterResource(id = imagenLocal),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Solo si no hay coincidencia mostramos el cuadro gris (o la URL si quisieras)
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.ShoppingCart, null, tint = Color.LightGray)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.producto.nombre,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 2
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