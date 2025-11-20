package com.example.levelup_gamer.ui.theme.Screen

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
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
fun CarritoScreen(
    navController: NavController,
    viewModel: CarritoViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel = viewModel()
) {
    val resumen by viewModel.resumen.collectAsState()
    val usuario by usuarioViewModel.usuario.collectAsState()
    val itemsCarrito = resumen.items

    // Actualizar resumen cuando cambia usuario o items
    LaunchedEffect(usuario.correo, itemsCarrito.size) {
        if (usuario.correo.isNotEmpty()) {
            viewModel.actualizarResumen(usuario.correo)
        }
    }

    val (textoDescuento, montoDescuento, porcentajeTexto) =
        if (usuario.correo.endsWith("@duocuc.cl")) {
            Triple("Descto 20%:", resumen.descuento.roundToInt(), "20%")
        } else {
            Triple("Descto 10%:", resumen.descuento.roundToInt(), "10%")
        }

    // Gradiente estilo Login
    val fondo = Brush.verticalGradient(
        listOf(
            Color(0xFF0A0A0A),
            Color(0xFF1A1A2E),
            Color(0xFF16213E)
        )
    )

    // Color para la top bar que coincida con el fondo
    val topBarColor = Color(0xFF0A0A0A) // Usamos el color más oscuro del gradiente

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Mi Carrito",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = topBarColor // Cambiar a color sólido que coincida
                )
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 8.dp,
                color = Color(0xFF1E1E2E)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal:", color = Color.LightGray)
                        Text(
                            "$${resumen.subtotal.roundToInt()} CLP",
                            color = Color.White
                        )
                    }

                    if (resumen.descuento > 0) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(textoDescuento, color = Color(0xFF00FF88))
                            Text("-$${montoDescuento} CLP", color = Color(0xFF00FF88))
                        }

                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (usuario.correo.endsWith("@duocuc.cl")) {
                                "🎓 Descuento especial estudiante Duoc - $porcentajeTexto"
                            } else {
                                "👍 Descuento regular de usuario - $porcentajeTexto"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF00FF88),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color(0xFF303040))
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total:", fontWeight = FontWeight.Bold, color = Color.White)
                        Text(
                            "$${resumen.total.roundToInt()} CLP",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00FF88)
                        )
                    }

                    if (resumen.descuento > 0) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (usuario.correo.endsWith("@duocuc.cl")) {
                                "💰 Ahorras: $${montoDescuento} CLP (20% descuento estudiante)"
                            } else {
                                "💰 Ahorras: $${montoDescuento} CLP (10% descuento regular)"
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF00FF88),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { navController.navigate("pago") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = itemsCarrito.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00FF88),
                            contentColor = Color.Black
                        )
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Comprar")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Proceder al Pago")
                    }
                }
            }
        },
        containerColor = Color.Transparent
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(fondo)
        ) {
            if (itemsCarrito.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
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
                        color = Color(0xFFA0A0A0)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { navController.navigate("catalogo") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00FF88),
                            contentColor = Color.Black
                        )
                    ) {
                        Text("Explorar Catálogo")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
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

                    item {
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFF1E1E2E)
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
                                    color = Color(0xFF00FF88),
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
                                    color = Color(0xFFA0A0A0)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = if (usuario.correo.endsWith("@duocuc.cl")) {
                                        "Cálculo: Subtotal ($${resumen.subtotal.roundToInt()}) × 20% = Ahorro de $${resumen.descuento.roundToInt()}"
                                    } else {
                                        "Cálculo: Subtotal ($${resumen.subtotal.roundToInt()}) × 10% = Ahorro de $${resumen.descuento.roundToInt()}"
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color(0xFF00FF88)
                                )
                            }
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
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                    color = Color(0xFFA0A0A0)
                )
                Text(
                    "$${(item.producto.precio * item.cantidad).roundToInt()} CLP",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF00FF88),
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    "$${item.producto.precio.roundToInt()} CLP c/u",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.LightGray
                )
            }

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

            IconButton(onClick = onEliminar) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar del carrito", tint = Color(0xFFFF6B6B))
            }
        }
    }
}