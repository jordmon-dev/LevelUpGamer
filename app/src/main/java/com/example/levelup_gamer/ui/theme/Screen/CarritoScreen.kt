package com.example.levelup_gamer.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamer.R
import com.example.levelup_gamer.modelo.CarritoItemUI
import com.example.levelup_gamer.viewmodel.CarritoViewModel
import com.example.levelup_gamer.viewmodel.UsuarioViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    navController: NavController,
    viewModel: CarritoViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val usuarioState by usuarioViewModel.usuario.collectAsState ()

    val resumen = uiState.resumen
    val usuario = usuarioState
    val itemsCarrito = resumen.items

    // Mostrar mensaje de error si existe
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            // Puedes usar un Snackbar aquí
            println("Error: $error")
        }
    }

    // Actualizar resumen cuando cambia usuario o cuando se monta la pantalla
    LaunchedEffect(usuario.email) {
        if (usuario.email.isNotEmpty()) {
            viewModel.actualizarResumen(usuario.email)
        }
    }

    val (textoDescuento, montoDescuento, porcentajeTexto) =
        if (usuario.email.endsWith("@duocuc.cl")) {
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

    val topBarColor = Color(0xFF0A0A0A)

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
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = topBarColor
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
                            text = if (usuario.email.endsWith("@duocuc.cl")) {
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
                            text = if (usuario.email.endsWith("@duocuc.cl")) {
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
                        onClick = {
                            if (itemsCarrito.isNotEmpty()) {
                                navController.navigate("pago")
                            }
                        },
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
            if (uiState.isLoading) {
                // Mostrar loading
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = Color(0xFF00FF88))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Cargando carrito...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }
            } else if (itemsCarrito.isEmpty()) {
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
                                viewModel.onCantidadChange(item, nuevaCantidad, usuario.email)
                            },
                            onEliminar = {
                                viewModel.onEliminar(item, usuario.email)
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
                                    text = if (usuario.email.endsWith("@duocuc.cl")) {
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
                                    text = if (usuario.email.endsWith("@duocuc.cl")) {
                                        "Estás disfrutando de un descuento especial del 20% por ser estudiante Duoc"
                                    } else {
                                        "Descuento regular del 10% aplicado a tu compra"
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFA0A0A0)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = if (usuario.email.endsWith("@duocuc.cl")) {
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
    item: CarritoItemUI,
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
            // --- IMAGEN AÑADIDA ---
            Image(
                painter = painterResource(id = item.imagen ?: R.drawable.banner_game),
                contentDescription = item.nombre,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            
            Spacer(Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    item.nombre,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    "Precio unitario: $${item.precio.roundToInt()} CLP",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFA0A0A0)
                )
                Text(
                    "$${(item.precio * item.cantidad).roundToInt()} CLP",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF00FF88),
                    modifier = Modifier.padding(top = 4.dp)
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
                    Icon(
                        Icons.Default.Remove,
                        contentDescription = "Reducir cantidad",
                        tint = Color.White
                    )
                }

                Text(
                    item.cantidad.toString(),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color.White
                )

                IconButton(
                    onClick = { onCantidadChange(item.cantidad + 1) }
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Aumentar cantidad",
                        tint = Color.White
                    )
                }
            }

            IconButton(onClick = onEliminar) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Eliminar del carrito",
                    tint = Color(0xFFFF6B6B)
                )
            }
        }
    }
}