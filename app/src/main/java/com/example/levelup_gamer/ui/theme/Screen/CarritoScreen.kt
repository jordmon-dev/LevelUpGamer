package com.example.levelup_gamer.ui.theme.Screen

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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamer.modelo.CarritoItemUI
import com.example.levelup_gamer.modelo.Usuario
import com.example.levelup_gamer.viewmodel.CarritoViewModel
import com.example.levelup_gamer.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    navController: NavController,
    viewModel: CarritoViewModel = viewModel(),
    usuarioViewModel: UsuarioViewModel = viewModel(),
) {
    // SOLUCIÓN 1: Si el ViewModel tiene una propiedad llamada 'usuario' que es un StateFlow
    // val usuarioState by usuarioViewModel.usuario.collectAsState()

    // SOLUCIÓN 2: Si el ViewModel tiene un estado que contiene el usuario
    // val usuarioUiState by usuarioViewModel.uiState.collectAsState()
    // val usuario = usuarioUiState.usuario

    // SOLUCIÓN 3: Obtener el email desde una fuente fija (para testing)
    val usuarioEmail = remember { "test@duocuc.cl" } // Cambia esto según tu lógica

    val uiState by viewModel.uiState.collectAsState()
    val resumen = uiState.resumen
    val itemsCarrito = resumen.items

    // Snackbar para mostrar mensajes de error
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Mostrar mensaje de error si existe
    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = error,
                    actionLabel = "OK"
                )
                // Limpiar el error después de mostrarlo
                viewModel.clearError()
            }
        }
    }

    // Actualizar resumen cuando se monta la pantalla
    LaunchedEffect(usuarioEmail) {
        if (usuarioEmail.isNotEmpty()) {
            viewModel.actualizarResumen(usuarioEmail)
        }
    }

    // Verificar si es estudiante Duoc
    val esEstudianteDuoc = rememberSaveable(usuarioEmail) {
        usuarioEmail.endsWith("@duocuc.cl", ignoreCase = true)
    }

    val porcentajeDescuento = if (esEstudianteDuoc) 0.20 else 0.10
    val textoDescuento = if (esEstudianteDuoc) "Descuento 20% Estudiante" else "Descuento 10%"
    val descripcionDescuento = if (esEstudianteDuoc) {
        "🎓 Descuento especial por ser estudiante Duoc"
    } else {
        "👍 Descuento regular de usuario"
    }

    val fondo = Brush.verticalGradient(
        listOf(
            Color(0xFF0A0A0A),
            Color(0xFF1A1A2E),
            Color(0xFF16213E)
        )
    )

    val topBarColor = Color(0xFF0A0A0A)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = topBarColor
                ),
                actions = {
                    if (itemsCarrito.isNotEmpty()) {
                        Text(
                            text = "${resumen.cantidadTotal} items",
                            color = Color(0xFF00FF88),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 8.dp,
                color = Color(0xFF1E1E2E)
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .fillMaxWidth()
                ) {
                    // Resumen de compra
                    Text(
                        "RESUMEN DE COMPRA",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF888888),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

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
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(textoDescuento, color = Color(0xFF00FF88))
                            Text("-$${resumen.descuento.roundToInt()} CLP", color = Color(0xFF00FF88))
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = descripcionDescuento,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF00FF88),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Divider(color = Color(0xFF303040))
                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total:", fontWeight = FontWeight.Bold, color = Color.White)
                        Text(
                            "$${resumen.total.roundToInt()} CLP",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00FF88),
                            fontSize = 20.sp
                        )
                    }

                    if (resumen.descuento > 0) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "💰 Ahorras: $${resumen.descuento.roundToInt()} CLP",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF00FF88),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (itemsCarrito.isNotEmpty()) {
                                // Pasar datos del carrito a la pantalla de pago
                                navController.navigate("pago")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        enabled = itemsCarrito.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00FF88),
                            contentColor = Color.Black,
                            disabledContainerColor = Color(0xFF505050),
                            disabledContentColor = Color(0xFFA0A0A0)
                        )
                    ) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            contentDescription = "Comprar",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Proceder al Pago",
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    if (itemsCarrito.isEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { navController.navigate("catalogo") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2A2A3A),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Explorar Catálogo")
                        }
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
            when {
                uiState.isLoading -> {
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
                            "Cargando tu carrito...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }

                itemsCarrito.isEmpty() -> {
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
                            modifier = Modifier.size(100.dp),
                            tint = Color(0xFF505050)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            "Tu carrito está vacío",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "Agrega productos desde el catálogo para comenzar",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFA0A0A0),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Button(
                            onClick = { navController.navigate("catalogo") },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF00FF88),
                                contentColor = Color.Black
                            ),
                            modifier = Modifier.width(200.dp)
                        ) {
                            Text("Ver Productos")
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(itemsCarrito) { item ->
                            ItemCarrito(
                                item = item,
                                onCantidadChange = { nuevaCantidad ->
                                    viewModel.onCantidadChange(item, nuevaCantidad, usuarioEmail)
                                },
                                onEliminar = {
                                    viewModel.onEliminar(item, usuarioEmail)
                                }
                            )
                        }

                        // Información de descuento
                        item {
                            Card(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 12.dp)
                                    .fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFF2A2A3A)
                                ),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 4.dp
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    ) {
                                        Text(
                                            text = if (esEstudianteDuoc) "🎓" else "👍",
                                            fontSize = 20.sp
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = if (esEstudianteDuoc) {
                                                "Descuento Especial Estudiante"
                                            } else {
                                                "Descuento para Usuarios"
                                            },
                                            style = MaterialTheme.typography.titleSmall,
                                            color = Color(0xFF00FF88),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    Text(
                                        text = if (esEstudianteDuoc) {
                                            "Estás disfrutando de un descuento exclusivo del 20% por ser estudiante Duoc UC"
                                        } else {
                                            "Descuento regular del 10% aplicado automáticamente"
                                        },
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFFA0A0A0),
                                        lineHeight = 18.sp
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Detalle del cálculo
                                    Surface(
                                        color = Color(0xFF1A1A2A),
                                        shape = MaterialTheme.shapes.small
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(12.dp)
                                        ) {
                                            Text(
                                                text = "Cálculo del descuento:",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color(0xFF888888)
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = "Subtotal: $${resumen.subtotal.roundToInt()} CLP",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Color.White
                                            )
                                            Text(
                                                text = "Descuento ${(porcentajeDescuento * 100).toInt()}%: -$${resumen.descuento.roundToInt()} CLP",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Color(0xFF00FF88)
                                            )
                                            Divider(
                                                color = Color(0xFF303040),
                                                modifier = Modifier.padding(vertical = 4.dp)
                                            )
                                            Text(
                                                text = "Total con descuento: $${resumen.total.roundToInt()} CLP",
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = Color(0xFF00FF88),
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Espacio extra al final
                        item {
                            Spacer(modifier = Modifier.height(100.dp))
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
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E2E)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Información del producto
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    item.nombre,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    "$${item.precio.roundToInt()} CLP c/u",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFA0A0A0)
                )

                if (item.descripcion?.isNotEmpty() == true) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        item.descripcion.take(60) + if (item.descripcion.length > 60) "..." else "",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF707070),
                        maxLines = 1
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Total por este item
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Total: ",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.LightGray
                    )
                    Text(
                        "$${(item.precio * item.cantidad).roundToInt()} CLP",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF00FF88),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Controles de cantidad y eliminar
            Column(
                horizontalAlignment = Alignment.End
            ) {
                // Botón eliminar
                IconButton(
                    onClick = onEliminar,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar del carrito",
                        tint = Color(0xFFFF6B6B),
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Controles de cantidad
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            if (item.cantidad > 1) onCantidadChange(item.cantidad - 1)
                        },
                        modifier = Modifier.size(36.dp),
                        enabled = item.cantidad > 1
                    ) {
                        Icon(
                            Icons.Default.Remove,
                            contentDescription = "Reducir cantidad",
                            tint = if (item.cantidad > 1) Color.White else Color(0xFF505050)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(36.dp)
                            .background(Color(0xFF2A2A3A)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            item.cantidad.toString(),
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    IconButton(
                        onClick = {
                            val nuevaCantidad = item.cantidad + 1
                            if (nuevaCantidad <= item.stock) {
                                onCantidadChange(nuevaCantidad)
                            }
                        },
                        modifier = Modifier.size(36.dp),
                        enabled = item.cantidad < item.stock
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Aumentar cantidad",
                            tint = if (item.cantidad < item.stock) Color.White else Color(0xFF505050)
                        )
                    }
                }

                // Indicador de stock
                if (item.stock > 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Stock: ${item.stock}",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (item.cantidad < item.stock) Color(0xFF00FF88) else Color(0xFFFF6B6B)
                    )
                }
            }
        }
    }
}