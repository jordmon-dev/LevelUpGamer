package com.example.levelup_gamer.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelup_gamer.R
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.viewmodel.CarritoViewModel
import com.example.levelup_gamer.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProductoScreen(
    navController: NavController,
    productoId: Int,
    productoViewModel: ProductoViewModel,
    carritoViewModel: CarritoViewModel
) {
    // Buscamos el producto por su ID
    val productos by productoViewModel.productos.collectAsState()
    val producto = productos.find { it.id == productoId }

    // Fondo degradado
    val brush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0A0A0A), Color(0xFF1A1A2E), Color(0xFF16213E))
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detalles", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().background(brush).padding(padding)) {

            if (producto != null) {
                // LÓGICA DE IMAGEN LOCAL (Misma que en Catálogo)
                val imagenLocal = when {
                    producto.nombre.contains("Cyberpunk", ignoreCase = true) -> R.drawable.cyberpunk
                    producto.nombre.contains("Witcher", ignoreCase = true) -> R.drawable.witcher3
                    producto.nombre.contains("Red Dead", ignoreCase = true) -> R.drawable.reddead2
                    producto.nombre.contains("Last of Us", ignoreCase = true) -> R.drawable.tlou2
                    producto.nombre.contains("Elden Ring", ignoreCase = true) -> R.drawable.eldenring
                    producto.nombre.contains("GTA", ignoreCase = true) -> R.drawable.gta5
                    producto.nombre.contains("PS5", ignoreCase = true) -> R.drawable.consola_ps5
                    producto.nombre.contains("Xbox", ignoreCase = true) -> R.drawable.consola_xbox
                    producto.nombre.contains("Switch", ignoreCase = true) -> R.drawable.consola_switch
                    producto.nombre.contains("PC Gamer", ignoreCase = true) -> R.drawable.pc_gamer
                    producto.nombre.contains("VR", ignoreCase = true) -> R.drawable.lentes_vr
                    producto.nombre.contains("Phone", ignoreCase = true) -> R.drawable.celular_gamer
                    else -> null
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // 1. IMAGEN GRANDE
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        if (imagenLocal != null) {
                            Image(
                                painter = painterResource(id = imagenLocal),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.DarkGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Sin Imagen", color = Color.LightGray)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 2. TÍTULO Y PRECIO
                    Text(
                        text = producto.nombre,
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = producto.categoria,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF00FF88)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$ ${producto.precio}",
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold
                        )

                        // Badge de Stock
                        Surface(
                            color = if (producto.stock > 0) Color(0xFF00FF88).copy(alpha = 0.2f) else Color.Red.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) {
                                Icon(
                                    if(producto.stock > 0) Icons.Default.CheckCircle else Icons.Default.Warning,
                                    null,
                                    tint = if (producto.stock > 0) Color(0xFF00FF88) else Color.Red,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (producto.stock > 0) "${producto.stock} Disponibles" else "Agotado",
                                    color = if (producto.stock > 0) Color(0xFF00FF88) else Color.Red,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // 3. DESCRIPCIÓN
                    Text(
                        text = "Descripción del Producto",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = producto.descripcion,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.LightGray,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    // 4. BOTÓN AGREGAR AL CARRITO
                    Button(
                        onClick = {
                            carritoViewModel.agregarProducto(
                                id = producto.id,
                                nombre = producto.nombre,
                                precio = producto.precio,
                                imagen = producto.imagen ?: ""
                            )
                            navController.popBackStack() // Volver al catálogo
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00FF88),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp),
                        enabled = producto.stock > 0
                    ) {
                        Icon(Icons.Default.AddShoppingCart, null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if(producto.stock > 0) "AGREGAR AL CARRITO" else "SIN STOCK",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            } else {
                // Si falla el ID
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Producto no encontrado", color = Color.White)
                }
            }
        }
    }
}