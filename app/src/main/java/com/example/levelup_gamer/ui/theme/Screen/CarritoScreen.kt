package com.example.levelup_gamer.ui.theme.Screen // <-- 1. PACKAGE CORREGIDO

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.viewmodel.CarritoViewModel
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Carrito(
    navController: NavController,
    // 2. RECIBE EL VIEWMODEL
    viewModel: CarritoViewModel = viewModel()
) {
    // 3. CONSUME EL ESTADO DEL VIEWMODEL
    val resumen by viewModel.resumen.collectAsState()
    val itemsCarrito = resumen.items

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
                    // Resumen de compra (usa valores del VM)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal:")
                        Text("$${resumen.subtotal.roundToInt()} CLP")
                    }

                    if (resumen.descuento > 0) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Descuento 10%:", color = Color(0xFF39FF14))
                            Text("-$${resumen.descuento.roundToInt()} CLP", color = Color(0xFF39FF14))
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
                            "$${resumen.total.roundToInt()} CLP",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E90FF)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        // 4. NAVEGACIÓN CORREGIDA A "home"
                        onClick = { navController.navigate("home") },
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
            // ... (Lógica de carrito vacío)
            Button(
                // 4. NAVEGACIÓN CORREGIDA A "catalogo"
                onClick = { navController.navigate("catalogo") }
            ) {
                Text("Explorar Catálogo")
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
                        // LLAMA A LOS MÉTODOS DEL VIEWMODEL
                        onCantidadChange = { nuevaCantidad ->
                            viewModel.onCantidadChange(item, nuevaCantidad)
                        },
                        onEliminar = {
                            viewModel.onEliminar(item)
                        }
                    )
                }
            }
        }
    }
}

// 5. SE ASUME QUE ESTA DATA CLASS AHORA ES 'CarritoItem'
// Y DEBERÍA VIVIR EN TU VIEWMODEL/MODELO, PERO LA DEJO AQUÍ PARA NO ROMPER EL FLUJO.
data class ProductoCarrito(
    val producto: Producto,
    val cantidad: Int
)


@Composable
fun ItemCarrito(
    // AHORA RECIBE PRODUCTOCARRITO (o el CarritoItem de tu VM)
    item: com.example.levelup_gamer.viewmodel.CarritoItem,
    onCantidadChange: (Int) -> Unit,
    onEliminar: () -> Unit
) {
    // ... (El resto del código ItemCarrito es correcto)
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
                // ... (El resto del texto)
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
                    Icon(Icons.Default.Remove, contentDescription = "Reducir cantidad")
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