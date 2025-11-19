package com.example.levelup_gamer.ui.theme.screen

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.CarritoItem
import com.example.levelup_gamer.viewmodel.CarritoViewModel
import com.example.levelup_gamer.viewmodel.UsuarioViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Carrito(
    navController: NavController,
    viewModel: CarritoViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel = viewModel()
) {
    val resumen by viewModel.resumen.collectAsState()
    val usuario by usuarioViewModel.usuario.collectAsState()
    val itemsCarrito = resumen.items

    // Efecto para actualizar el resumen cuando cambien los datos
    LaunchedEffect(usuario.correo, itemsCarrito.size) {
        if (usuario.correo.isNotEmpty()) {
            viewModel.actualizarResumen(usuario.correo)
        }
    }

    // Validación if-else para determinar el texto y cálculo del descuento
    val (textoDescuento, montoDescuento, porcentajeTexto) = if (usuario.correo.endsWith("@duocuc.cl")) {
        Triple("Descto 20%:", resumen.descuento.roundToInt(), "20%")
    } else {
        Triple("Descto 10%:", resumen.descuento.roundToInt(), "10%")
    }

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
                        Text("$${resumen.subtotal.roundToInt()} CLP")
                    }

                    if (resumen.descuento > 0) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(textoDescuento, color = Color(0xFF39FF14))
                            Text("-$${montoDescuento} CLP", color = Color(0xFF39FF14))
                        }

                        // Mostrar información del tipo de descuento
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (usuario.correo.endsWith("@duocuc.cl")) {
                                "🎓 Descuento especial estudiante Duoc - $porcentajeTexto"
                            } else {
                                "👍 Descuento regular de usuario - $porcentajeTexto"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF39FF14),
                            modifier = Modifier.fillMaxWidth()
                        )
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

                    // Mostrar ahorro total con validación if-else
                    if (resumen.descuento > 0) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (usuario.correo.endsWith("@duocuc.cl")) {
                                "💰 Ahorras: $${montoDescuento} CLP (20% descuento estudiante)"
                            } else {
                                "💰 Ahorras: $${montoDescuento} CLP (10% descuento regular)"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF39FF14),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { navController.navigate("pago") },
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
            // Carrito vacío
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.ShoppingCart,
                    contentDescription = "Carrito vacío",
                    modifier = Modifier.size(80.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Tu carrito está vacío",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Agrega algunos productos para continuar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { navController.navigate("catalogo") }
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
                            viewModel.onCantidadChange(item, nuevaCantidad, usuario.correo)
                        },
                        onEliminar = {
                            viewModel.onEliminar(item, usuario.correo)
                        }
                    )
                }

                // Mostrar información del descuento aplicado con validación if-else
                item {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (usuario.correo.endsWith("@duocuc.cl")) {
                                Color(0xFF1B5E20) // Verde oscuro para 20%
                            } else {
                                Color(0xFF1A237E) // Azul oscuro para 10%
                            }
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = if (usuario.correo.endsWith("@duocuc.cl")) {
                                    "🎓 Descuento Estudiante Duoc - 20%"
                                } else {
                                    "👍 Descuento Usuario - 10%"
                                },
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (usuario.correo.endsWith("@duocuc.cl")) {
                                    "Estás disfrutando de un descuento especial del 20% por ser estudiante Duoc"
                                } else {
                                    "Descuento regular del 10% aplicado a tu compra"
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.LightGray
                            )

                            // Información adicional de cálculo
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (usuario.correo.endsWith("@duocuc.cl")) {
                                    "Cálculo: Subtotal ($${resumen.subtotal.roundToInt()}) × 20% = Ahorro de $${resumen.descuento.roundToInt()}"
                                } else {
                                    "Cálculo: Subtotal ($${resumen.subtotal.roundToInt()}) × 10% = Ahorro de $${resumen.descuento.roundToInt()}"
                                },
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF39FF14)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemCarrito(
    item: CarritoItem,
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
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    item.producto.categoria,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    "$${(item.producto.precio * item.cantidad).roundToInt()} CLP",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF1E90FF),
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    "$${item.producto.precio.roundToInt()} CLP c/u",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray
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
                    Icon(Icons.Default.Remove, contentDescription = "Reducir cantidad", tint = Color.White)
                }

                Text(
                    item.cantidad.toString(),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color.White
                )

                IconButton(
                    onClick = { onCantidadChange(item.cantidad + 1) }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Aumentar cantidad", tint = Color.White)
                }
            }

            // Botón eliminar
            IconButton(onClick = onEliminar) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar del carrito", tint = Color.Red)
            }
        }
    }
}