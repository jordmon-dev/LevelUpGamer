package com.example.levelup_gamer.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAgregarProductoScreen(
    navController: NavController,
    viewModel: ProductoViewModel
) {
    // Estados del formulario
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }

    val context = LocalContext.current

    // Gradiente de fondo (El mismo de Perfil)
    val fondo = Brush.verticalGradient(
        listOf(Color(0xFF0A0A0A), Color(0xFF1A1A2E), Color(0xFF16213E))
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Panel Admin",
                        color = Color(0xFF00FF88),
                        fontWeight = FontWeight.Bold
                    )
                },
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(fondo)
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Título llamativo
                Text(
                    "Nuevo Producto",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    "Agrega ítems al inventario global",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // --- FORMULARIO CON ESTILO NEÓN ---
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    modifier = Modifier.border(1.dp, Color(0xFF2A2A3E), RoundedCornerShape(16.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        InputNeon(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = "Nombre del Producto",
                            icon = Icons.Default.ShoppingBag
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Box(Modifier.weight(1f)) {
                                InputNeon(
                                    value = precio,
                                    onValueChange = { precio = it },
                                    label = "Precio",
                                    icon = Icons.Default.AttachMoney,
                                    keyboardType = KeyboardType.Number
                                )
                            }
                            Box(Modifier.weight(1f)) {
                                InputNeon(
                                    value = stock,
                                    onValueChange = { stock = it },
                                    label = "Stock",
                                    icon = Icons.Default.Inventory,
                                    keyboardType = KeyboardType.Number
                                )
                            }
                        }

                        InputNeon(
                            value = categoria,
                            onValueChange = { categoria = it },
                            label = "Categoría (Ej: PS5, Xbox)",
                            icon = Icons.Default.Category
                        )

                        InputNeon(
                            value = imagenUrl,
                            onValueChange = { imagenUrl = it },
                            label = "URL de Imagen (http...)",
                            icon = Icons.Default.Image
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // --- BOTÓN DE GUARDAR ---
                Button(
                    onClick = {
                        if (nombre.isNotBlank() && precio.isNotBlank()) {

                            // LLAMADA REAL AL VIEWMODEL
                            viewModel.agregarProducto(
                                nombre = nombre,
                                precio = precio.toIntOrNull() ?: 0,
                                stock = stock.toIntOrNull() ?: 0,
                                categoria = categoria,
                                imagen = imagenUrl
                            ) { exito ->
                                // Este código se ejecuta cuando el servidor responde
                                if (exito) {
                                    Toast.makeText(context, "✅ ¡Producto Guardado en BD!", Toast.LENGTH_LONG).show()
                                    navController.popBackStack()
                                } else {
                                    Toast.makeText(context, "❌ Error al guardar", Toast.LENGTH_SHORT).show()
                                }
                            }

                        } else {
                            Toast.makeText(context, "⚠️ Faltan datos", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00FF88),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Add, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Guardar Producto",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// Componente reutilizable para inputs estilo neón
@Composable
fun InputNeon(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, null, tint = Color(0xFF00FF88)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF00FF88),
            unfocusedBorderColor = Color(0xFF444466),
            cursorColor = Color(0xFF00FF88),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedLabelColor = Color(0xFF00FF88),
            unfocusedLabelColor = Color(0xFFA0A0A0),
            focusedLeadingIconColor = Color(0xFF00FF88),
            unfocusedLeadingIconColor = Color(0xFF444466)
        )
    )
}