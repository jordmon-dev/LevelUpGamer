package com.example.levelup_gamer.ui.theme.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.CarritoViewModel
import com.example.levelup_gamer.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagoScreen(
    navController: NavController,
    carritoViewModel: CarritoViewModel, // Quitamos el = viewModel() para obligar a pasar el compartido
    usuarioViewModel: UsuarioViewModel
) {
    // Estado del Carrito (Productos y Total)
    val uiState by carritoViewModel.uiState.collectAsState()

    // Estado del Usuario (Para rellenar dirección si existe)
    val usuarioState by usuarioViewModel.usuarioState.collectAsState()

    val context = LocalContext.current

    // Variables del formulario de pago
    var direccion by remember { mutableStateOf(usuarioState.nombre + " - Dirección Demo") } // Simulamos dirección
    var numeroTarjeta by remember { mutableStateOf("") }
    var fechaVencimiento by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    val brush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0A0A0A), Color(0xFF1A1A2E), Color(0xFF16213E))
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Finalizar Compra", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // --- RESUMEN DE COMPRA ---
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Resumen del Pedido", color = Color(0xFF00FF88), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(8.dp))

                        // CORRECCIÓN: Usamos uiState.items directamente (sin .resumen)
                        uiState.items.forEach { item ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("${item.cantidad}x ${item.producto.nombre}", color = Color.White, fontSize = 14.sp)
                                Text("$ ${item.producto.precio.toInt() * item.cantidad}", color = Color.Gray, fontSize = 14.sp)
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.Gray)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("TOTAL A PAGAR", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                            // CORRECCIÓN: Usamos uiState.total directamente
                            Text("$ ${uiState.total.toInt()}", color = Color(0xFF00FF88), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }
                    }
                }

                // --- DATOS DE ENVÍO ---
                Text("Datos de Envío", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)

                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text("Dirección de entrega") },
                    leadingIcon = { Icon(Icons.Default.LocationOn, null, tint = Color(0xFF00FF88)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors()
                )

                // --- DATOS DE PAGO ---
                Text("Método de Pago", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)

                OutlinedTextField(
                    value = numeroTarjeta,
                    onValueChange = { if (it.length <= 16) numeroTarjeta = it },
                    label = { Text("Número de Tarjeta") },
                    leadingIcon = { Icon(Icons.Default.CreditCard, null, tint = Color(0xFF00FF88)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors()
                )

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    OutlinedTextField(
                        value = fechaVencimiento,
                        onValueChange = { if (it.length <= 5) fechaVencimiento = it },
                        label = { Text("MM/AA") },
                        modifier = Modifier.weight(1f),
                        colors = textFieldColors()
                    )
                    OutlinedTextField(
                        value = cvv,
                        onValueChange = { if (it.length <= 3) cvv = it },
                        label = { Text("CVV") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        colors = textFieldColors()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // --- BOTÓN FINALIZAR ---
                Button(
                    onClick = {
                        if (numeroTarjeta.isNotEmpty() && cvv.isNotEmpty()) {
                            // Simular pago exitoso
                            Toast.makeText(context, "¡Pago Exitoso! Enviando pedido...", Toast.LENGTH_LONG).show()

                            // Vaciar el carrito
                            carritoViewModel.vaciarCarrito()

                            // Volver al Home
                            navController.navigate("home") {
                                popUpTo("home") { inclusive = true }
                            }
                        } else {
                            Toast.makeText(context, "Completa los datos de pago", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF88), contentColor = Color.Black),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("PAGAR AHORA", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun textFieldColors() = TextFieldDefaults.colors(
    focusedContainerColor = Color(0xFF1E1E2E),
    unfocusedContainerColor = Color(0xFF1E1E2E),
    focusedTextColor = Color.White,
    unfocusedTextColor = Color.White,
    focusedIndicatorColor = Color(0xFF00FF88),
    unfocusedIndicatorColor = Color.Gray,
    focusedLabelColor = Color(0xFF00FF88),
    unfocusedLabelColor = Color.Gray
)