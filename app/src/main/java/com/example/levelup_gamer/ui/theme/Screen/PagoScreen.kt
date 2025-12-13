package com.example.levelup_gamer.ui.theme.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.CarritoViewModel

// O alternativamente:

import androidx.compose.runtime.LaunchedEffect
import com.example.levelup_gamer.modelo.CarritoItemUI


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagoScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel = viewModel()
) {
<<<<<<< HEAD
    // SOLUCIÓN: Usar el uiState del ViewModel en lugar de resumen directo
    val uiState by carritoViewModel.uiState.collectAsState()
    val resumen = uiState.resumen

=======
    val resumenState = carritoViewModel.actualizarResumen(email = usuario.email)
    val uiState by carritoViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(usuario.email) {
        carritoViewModel.actualizarResumen(usuario.email)
    }

    val resumen = uiState.resumen
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
    var metodoPagoSeleccionado by remember { mutableStateOf("") }
    var mostrarFormularioTarjeta by remember { mutableStateOf(false) }

    // Muestra loading si es necesario
    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    // Muestra error si existe
    uiState.errorMessage?.let { error ->
        LaunchedEffect(error) {
            // Puedes mostrar un snackbar o manejar el error
            println("Error: $error")
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Método de Pago") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            // Resumen del pedido
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "Resumen del Pedido",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

<<<<<<< HEAD
                    // Productos en el carrito - SOLUCIÓN: Usar items de resumen
                    resumen.items.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // CORRECCIÓN: item.nombre en lugar de item.producto.nombre
                            Text(
                                "${item.cantidad}x ${item.nombre}",
                                color = Color.LightGray,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            // CORRECCIÓN: item.precio en lugar de item.producto.precio
                            Text(
                                "$${(item.precio * item.cantidad).toInt()} CLP",
                                color = Color.LightGray,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
=======
                    ProductosResumen(resumen.items)
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0

                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Totales
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal:", color = Color.White)
                        Text("$${resumen.subtotal.toInt()} CLP", color = Color.White)
                    }

                    if (resumen.descuento > 0) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Descuento:", color = Color(0xFF39FF14))
                            Text("-$${resumen.descuento.toInt()} CLP", color = Color(0xFF39FF14))
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total:", color = Color.White, fontWeight = FontWeight.Bold)
                        Text(
                            "$${resumen.total.toInt()} CLP",
                            color = Color(0xFF1E90FF),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Selección de método de pago
            Text(
                "Selecciona método de pago",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Column(modifier = Modifier.selectableGroup()) {
                // Tarjeta de crédito
                MetodoPagoItem(
                    titulo = "Tarjeta de Crédito",
                    subtitulo = "Visa, Mastercard, American Express",
                    icono = Icons.Default.CreditCard,
                    seleccionado = metodoPagoSeleccionado == "credito",
                    onClick = {
                        metodoPagoSeleccionado = "credito"
                        mostrarFormularioTarjeta = true
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Tarjeta de débito
                MetodoPagoItem(
                    titulo = "Tarjeta de Débito",
                    subtitulo = "Redcompra, Visa Débito, Mastercard Débito",
                    icono = Icons.Default.AccountBalanceWallet,
                    seleccionado = metodoPagoSeleccionado == "debito",
                    onClick = {
                        metodoPagoSeleccionado = "debito"
                        mostrarFormularioTarjeta = true
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Transferencia bancaria
                MetodoPagoItem(
                    titulo = "Transferencia Bancaria",
                    subtitulo = "Pago via transferencia electrónica",
                    icono = Icons.Default.AccountBalance,
                    seleccionado = metodoPagoSeleccionado == "transferencia",
                    onClick = {
                        metodoPagoSeleccionado = "transferencia"
                        mostrarFormularioTarjeta = false
                    }
                )
            }

            // Formulario de tarjeta (solo para crédito/débito)
            if (mostrarFormularioTarjeta && (metodoPagoSeleccionado == "credito" || metodoPagoSeleccionado == "debito")) {
                Spacer(modifier = Modifier.height(24.dp))
                FormularioTarjeta(
                    esCredito = metodoPagoSeleccionado == "credito"
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // BOTÓN DE CONFIRMAR PAGO
            Button(
                onClick = {
<<<<<<< HEAD
                    // 1. Navega a la pantalla de confirmación primero
=======
                    // 1. Limpia el carrito
                    carritoViewModel.limpiarCarrito(usuario.email)
                    // 2. Navega a la pantalla de confirmación
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
                    navController.navigate("confirmacion") {
                        popUpTo("home") { inclusive = false }
                    }
                    // 2. Luego limpia el carrito (esto se podría hacer después de confirmación)
                    // carritoViewModel.limpiarCarrito()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = metodoPagoSeleccionado.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E90FF)
                )
            ) {
                Icon(Icons.Default.Payment, contentDescription = "Pagar")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Pagar $${resumen.total.toInt()} CLP",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun MetodoPagoItem(
    titulo: String,
    subtitulo: String,
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (seleccionado) Color(0xFF1E90FF).copy(alpha = 0.2f)
            else Color(0xFF1A1A1A)
        ),
        border = if (seleccionado) CardDefaults.outlinedCardBorder() else null
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = if (seleccionado) Color(0xFF1E90FF) else Color.Gray,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    titulo,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (seleccionado) Color(0xFF1E90FF) else Color.White,
                    fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal
                )
                Text(
                    subtitulo,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (seleccionado) Color(0xFF1E90FF).copy(alpha = 0.8f) else Color.Gray
                )
            }
            RadioButton(
                selected = seleccionado,
                onClick = null,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color(0xFF1E90FF)
                )
            )
        }
    }
}

@Composable
fun FormularioTarjeta(esCredito: Boolean) {
    var numeroTarjeta by remember { mutableStateOf("") }
    var nombreTitular by remember { mutableStateOf("") }
    var fechaVencimiento by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                if (esCredito) "Información de Tarjeta de Crédito" else "Información de Tarjeta de Débito",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Número de tarjeta
            OutlinedTextField(
                value = numeroTarjeta,
                onValueChange = { input ->
                    val filtered = input.filter { it.isDigit() }
                    if (filtered.length <= 16) {
                        numeroTarjeta = filtered
                    }
                },
                label = { Text("Número de tarjeta") },
                placeholder = { Text("1234567890123456") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Nombre del titular
            OutlinedTextField(
                value = nombreTitular,
                onValueChange = { nombreTitular = it },
                label = { Text("Nombre del titular") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Fecha vencimiento y CVV
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = fechaVencimiento,
                    onValueChange = { input ->
                        val filtered = input.filter { it.isDigit() }
                        fechaVencimiento = when {
                            filtered.length > 4 -> fechaVencimiento
                            filtered.length > 2 -> "${filtered.substring(0, 2)}/${filtered.substring(2)}"
                            else -> filtered
                        }
                    },
                    label = { Text("MM/AA") },
                    placeholder = { Text("12/25") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = cvv,
                    onValueChange = {
                        if (it.length <= 3 && it.all { char -> char.isDigit() }) {
                            cvv = it
                        }
                    },
                    label = { Text("CVV") },
                    placeholder = { Text("123") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun ProductosResumen(items: List<CarritoItemUI?>) {
    val itemsSeguros = items.filterNotNull()

    if (itemsSeguros.isNotEmpty()) {
        itemsSeguros.forEach { item ->
            item.producto?.let { producto ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "${item.cantidad}x ${producto.nombre}",
                        color = Color.LightGray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "$${(producto.precio * item.cantidad).roundToInt()} CLP",
                        color = Color.LightGray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    } else {
        Text(
            "No hay productos en el carrito",
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}