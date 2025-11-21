package com.example.levelup_gamer.ui.theme.Screen

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.CarritoViewModel
import com.example.levelup_gamer.viewmodel.PagoViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagoScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel = viewModel(),
    pagoViewModel: PagoViewModel = viewModel()
) {
    val resumen by carritoViewModel.resumen.collectAsState()

    var metodoPagoSeleccionado by remember { mutableStateOf("") }
    var mostrarFormularioTarjeta by remember { mutableStateOf(false) }

    // Estado del formulario de tarjeta
    var numeroTarjeta by remember { mutableStateOf("") }
    var nombreTitular by remember { mutableStateOf("") }
    var fechaVencimiento by remember { mutableStateOf("") } // MM/AA
    var cvv by remember { mutableStateOf("") }

    var errorMensaje by remember { mutableStateOf<String?>(null) }
    var pagoExitoso by remember { mutableStateOf(false) }

    // Fondo tipo neón como otras pantallas
    val fondo = Brush.verticalGradient(
        listOf(
            Color(0xFF0A0A0A),
            Color(0xFF1A1A2E),
            Color(0xFF16213E)
        )
    )

    // Para saber si el botón de pago debe estar habilitado
    val tarjetaDatosCompletos =
        numeroTarjeta.length in 13..19 &&
                nombreTitular.isNotBlank() &&
                fechaVencimiento.length >= 4 &&
                cvv.length == 3

    val botonHabilitado = when (metodoPagoSeleccionado) {
        "transferencia" -> true
        "credito", "debito" -> tarjetaDatosCompletos
        else -> false
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Método de Pago",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF0A0A0A)
                )
            )
        },
        containerColor = Color.Transparent
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(fondo)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
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

                    // Productos en el carrito
                    resumen.items.forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "${item.cantidad}x ${item.producto.nombre}",
                                color = Color.LightGray,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                "$${(item.producto.precio * item.cantidad).roundToInt()} CLP",
                                color = Color.LightGray,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))

                    // Totales
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal:", color = Color.White)
                        Text("$${resumen.subtotal.roundToInt()} CLP", color = Color.White)
                    }

                    if (resumen.descuento > 0) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Descuento:", color = Color(0xFF39FF14))
                            Text(
                                "-$${resumen.descuento.roundToInt()} CLP",
                                color = Color(0xFF39FF14)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total:", color = Color.White, fontWeight = FontWeight.Bold)
                        Text(
                            "$${resumen.total.roundToInt()} CLP",
                            color = Color(0xFF00FF88),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Mensajes de error / éxito
            if (errorMensaje != null) {
                Text(
                    text = errorMensaje!!,
                    color = Color(0xFFFF6B6B),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            if (pagoExitoso) {
                Text(
                    text = "✅ Pago realizado con éxito",
                    color = Color(0xFF00FF88),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

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
                        errorMensaje = null
                        pagoExitoso = false
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
                        errorMensaje = null
                        pagoExitoso = false
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Transferencia bancaria
                MetodoPagoItem(
                    titulo = "Transferencia Bancaria",
                    subtitulo = "Pago vía transferencia electrónica",
                    icono = Icons.Default.AccountBalance,
                    seleccionado = metodoPagoSeleccionado == "transferencia",
                    onClick = {
                        metodoPagoSeleccionado = "transferencia"
                        mostrarFormularioTarjeta = false
                        errorMensaje = null
                        pagoExitoso = false
                    }
                )
            }

            // Formulario de tarjeta (solo para crédito/débito)
            if (mostrarFormularioTarjeta &&
                (metodoPagoSeleccionado == "credito" || metodoPagoSeleccionado == "debito")
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                FormularioTarjeta(
                    esCredito = metodoPagoSeleccionado == "credito",
                    numeroTarjeta = numeroTarjeta,
                    onNumeroTarjetaChange = { nuevo ->
                        // Solo dígitos, máximo 19 (internacional)
                        if (nuevo.length <= 19 && nuevo.all { it.isDigit() }) {
                            numeroTarjeta = nuevo
                        }
                    },
                    nombreTitular = nombreTitular,
                    onNombreTitularChange = { nombreTitular = it },
                    fechaVencimiento = fechaVencimiento,
                    onFechaVencimientoChange = { nuevo ->
                        if (nuevo.length <= 5 && nuevo.matches(Regex("^\\d{0,2}/?\\d{0,2}\$"))) {
                            fechaVencimiento = nuevo
                        }
                    },
                    cvv = cvv,
                    onCvvChange = { nuevo ->
                        if (nuevo.length <= 3 && nuevo.all { it.isDigit() }) {
                            cvv = nuevo
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de confirmar pago
            Button(
                onClick = {
                    errorMensaje = null
                    pagoExitoso = false

                    when (metodoPagoSeleccionado) {
                        "transferencia" -> {
                            // Pago simple por transferencia
                            pagoViewModel.actualizarTotal(resumen.total)
                            pagoViewModel.confirmarPago()
                            carritoViewModel.limpiarCarrito()
                            navController.navigate("confirmacion") {
                                popUpTo("home") { inclusive = false }
                            }
                        }

                        "credito", "debito" -> {
                            // Validaciones adicionales de tarjeta
                            if (!luhnCheck(numeroTarjeta)) {
                                errorMensaje = "Número de tarjeta inválido."
                                return@Button
                            }
                            if (!validarFechaExpiracion(fechaVencimiento)) {
                                errorMensaje = "Fecha de expiración inválida."
                                return@Button
                            }
                            if (cvv.length != 3) {
                                errorMensaje = "CVV inválido (3 dígitos)."
                                return@Button
                            }

                            // Todo ok
                            pagoViewModel.actualizarTotal(resumen.total)
                            pagoViewModel.confirmarPago()
                            carritoViewModel.limpiarCarrito()
                            pagoExitoso = true

                            navController.navigate("confirmacion") {
                                popUpTo("home") { inclusive = false }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = botonHabilitado,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00FF88),
                    contentColor = Color.Black,
                    disabledContainerColor = Color(0xFF444444),
                    disabledContentColor = Color.Gray
                )
            ) {
                Icon(Icons.Default.Payment, contentDescription = "Pagar")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Pagar $${resumen.total.roundToInt()} CLP",
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
                    // Permite solo dígitos y limita a 16 caracteres
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
                // Fecha de Vencimiento (MM/AA) - UX Mejorada
                OutlinedTextField(
                    value = fechaVencimiento,
                    onValueChange = { input ->
                        val filtered = input.filter { it.isDigit() }
                        // Aceptar máximo 4 dígitos e insertar el "/" automáticamente
                        fechaVencimiento = when {
                            filtered.length > 4 -> fechaVencimiento // Si excede 4 dígitos, no hacer nada
                            filtered.length > 2 -> "${filtered.substring(0, 2)}/${filtered.substring(2)}" // Inserta / después del mes
                            else -> filtered // Mes sin /
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

/**
 * Valida número de tarjeta con algoritmo de Luhn.
 */
private fun luhnCheck(number: String): Boolean {
    val digits = number.filter { it.isDigit() }
    if (digits.length !in 13..19) return false

    var sum = 0
    var isSecond = false

    for (i in digits.length - 1 downTo 0) {
        var d = digits[i] - '0'
        if (isSecond) {
            d *= 2
            if (d > 9) d -= 9
        }
        sum += d
        isSecond = !isSecond
    }
    return sum % 10 == 0
}

/**
 * Valida fecha de expiración en formato MM/AA y que no esté vencida.
 */
private fun validarFechaExpiracion(exp: String): Boolean {
    val partes = exp.split("/")
    if (partes.size != 2) return false

    val mes = partes[0].toIntOrNull() ?: return false
    val anio = partes[1].toIntOrNull() ?: return false

    // MM debe estar entre 1 y 12
    if (mes !in 1..12) return false

    // Año en formato 2 dígitos -> 2000 + AA (ej: "25" -> 2025)
    val anioCompleto = 2000 + anio

    val calendario = java.util.Calendar.getInstance()
    val actualMes = calendario.get(java.util.Calendar.MONTH) + 1  // MONTH es 0-based
    val actualAnio = calendario.get(java.util.Calendar.YEAR)

    return when {
        anioCompleto > actualAnio -> true
        anioCompleto < actualAnio -> false
        else -> mes >= actualMes   // mismo año: mes debe ser >= mes actual
    }
}


