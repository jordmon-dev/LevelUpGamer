/*package com.example.levelup_gamer.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove // ← Este es el correcto
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelup_gamer.model.Producto
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Carrito(navController: NavController) {
    var itemsCarrito by remember {
        mutableStateOf(
            listOf(
                ProductoCarrito(
                    producto = Producto(
                        codigo = "AC001",
                        nombre = "Controlador Xbox Series X",
                        precio = 59990.0,
                        categoria = "Accesorios",
                        descripcion = "Control inalámbrico"
                    ),
                    cantidad = 1
                ),
                ProductoCarrito(
                    producto = Producto(
                        codigo = "PP001",
                        nombre = "Polera Gamer Personalizada",
                        precio = 14990.0,
                        categoria = "Poleras",
                        descripcion = "Polera personalizable"
                    ),
                    cantidad = 2
                )
            )
        )
    }

    val subtotal = itemsCarrito.sumOf { it.producto.precio * it.cantidad }
    val descuento = if (subtotal > 50000) subtotal * 0.1 else 0.0
    val total = subtotal - descuento

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mi Carrito") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    // Resumen de compra
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal:")
                        Text("$${subtotal.roundToInt()} CLP")
                    }

                    if (descuento > 0) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Descuento 10%:", color = Color(0xFF39FF14))
                            Text("-$${descuento.roundToInt()} CLP", color = Color(0xFF39FF14))
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total:", fontWeight = FontWeight.Bold)
                        Text(
                            "$${total.roundToInt()} CLP",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E90FF)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            // Aquí puedes agregar lógica de pago
                            navController.navigate("home_screen")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = itemsCarrito.isNotEmpty()
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Comprar")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Proceder al Pago")
                    }
                }
            }
        }
    ) { innerPadding ->
        if (itemsCarrito.isEmpty()) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "Carrito vacío",
                    modifier = Modifier.size(64.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Tu carrito está vacío",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Agrega algunos productos desde el catálogo",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { navController.navigate("catalogo_screen") }
                ) {
                    Text("Explorar Catálogo")
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                items(itemsCarrito) { item ->
                    ItemCarrito(
                        item = item,
                        onCantidadChange = { nuevaCantidad ->
                            itemsCarrito = itemsCarrito.map {
                                if (it.producto.codigo == item.producto.codigo) {
                                    it.copy(cantidad = nuevaCantidad)
                                } else it
                            }
                        },
                        onEliminar = {
                            itemsCarrito = itemsCarrito.filter { it.producto.codigo != item.producto.codigo }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ItemCarrito(
    item: ProductoCarrito,
    onCantidadChange: (Int) -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Info del producto
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    item.producto.nombre,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )
                Text(
                    item.producto.categoria,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    "$${item.producto.precio.roundToInt()} CLP",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF1E90FF)
                )
            }

            // Controles de cantidad
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        if (item.cantidad > 1) onCantidadChange(item.cantidad - 1)
                    }
                ) {
                    Icon(Icons.Default.Remove, contentDescription = "Reducir cantidad") // ← Ahora funciona
                }

                Text(
                    item.cantidad.toString(),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color.White
                )

                IconButton(
                    onClick = { onCantidadChange(item.cantidad + 1) }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Aumentar cantidad")
                }
            }

            // Botón eliminar
            IconButton(onClick = onEliminar) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar del carrito", tint = Color.Red)
            }
        }
    }
}

data class ProductoCarrito(
    val producto: Producto,
    val cantidad: Int
)*/