package com.example.levelup_gamer.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelup_gamer.R // ✅ Importante para acceder a tus imágenes
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.viewmodel.CarritoViewModel
import com.example.levelup_gamer.viewmodel.ProductoViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    navController: NavController,
    productoViewModel: ProductoViewModel,
    carritoViewModel: CarritoViewModel
) {
    val productos by productoViewModel.productos.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    // Filtramos la lista según lo que escriba el usuario
    val productosFiltrados = productos.filter {
        it.nombre.contains(searchQuery, ignoreCase = true)
    }

    // Fondo degradado
    val brush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0A0A0A), Color(0xFF1A1A2E), Color(0xFF16213E))
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Catálogo de Juegos", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
                    }
                },
                actions = {
                    // Botón para ir al carrito directo desde aquí
                    IconButton(onClick = { navController.navigate("carrito") }) {
                        Icon(Icons.Default.ShoppingCart, null, tint = Color(0xFF00FF88))
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
                .background(brush)
                .padding(padding)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                // --- BARRA DE BÚSQUEDA ---
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Buscar juego...") },
                    leadingIcon = { Icon(Icons.Default.Search, null, tint = Color(0xFF00FF88)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF00FF88),
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color(0xFF00FF88),
                        focusedLabelColor = Color(0xFF00FF88),
                        unfocusedLabelColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // --- LISTA DE PRODUCTOS ---
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(productosFiltrados) { producto ->
                        ProductoItem(
                            producto = producto,
                            onAgregar = {
                                // ✅ CORRECCIÓN: Pasamos los datos uno por uno como pide tu función
                                carritoViewModel.agregarProducto(
                                    id = producto.id,         // Asegúrate que id sea Int
                                    nombre = producto.nombre,
                                    precio = producto.precio,
                                    imagen = producto.imagen ?: "")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductoItem(producto: Producto, onAgregar: () -> Unit) {
    var isAdded by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (isAdded) 1.2f else 1f, label = "scale")

    // ✅ LÓGICA DE MAPEO DE IMÁGENES
    // Detectamos el nombre del juego para asignar la foto local correcta
    val imagenLocal = when {
        producto.nombre.contains("Cyberpunk", ignoreCase = true) -> R.drawable.cyberpunk
        producto.nombre.contains("Witcher", ignoreCase = true) -> R.drawable.witcher3
        producto.nombre.contains("Red Dead", ignoreCase = true) -> R.drawable.reddead2
        producto.nombre.contains("Last of Us", ignoreCase = true) -> R.drawable.tlou2
        producto.nombre.contains("Elden Ring", ignoreCase = true) -> R.drawable.eldenring
        producto.nombre.contains("GTA", ignoreCase = true) || producto.nombre.contains("Grand Theft", ignoreCase = true) -> R.drawable.gta5

        //VIDEO JUEGOS

        producto.nombre.contains("PS5", ignoreCase = true) -> R.drawable.consola_ps5
        producto.nombre.contains("Xbox", ignoreCase = true) -> R.drawable.consola_xbox
        producto.nombre.contains("Switch", ignoreCase = true) -> R.drawable.consola_switch
        producto.nombre.contains("PC Gamer", ignoreCase = true) -> R.drawable.pc_gamer
        producto.nombre.contains("VR", ignoreCase = true) -> R.drawable.lentes_vr
        producto.nombre.contains("Phone", ignoreCase = true) -> R.drawable.celular_gamer




        else -> null // Si es un juego nuevo desconocido, devolverá null
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp), // Altura fija para uniformidad
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E).copy(0.9f)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize() // Ocupar todo el espacio de la tarjeta
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // --- IMAGEN DEL PRODUCTO ---
            if (imagenLocal != null) {
                // CASO A: Tenemos la imagen en drawable
                Image(
                    painter = painterResource(id = imagenLocal),
                    contentDescription = producto.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(90.dp) // Tamaño cuadrado
                        .clip(RoundedCornerShape(8.dp))
                )
            } else {
                // CASO B: Juego desconocido (Fallback gris elegante)
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.ShoppingCart, null, tint = Color.LightGray)
                }
            }
            // ---------------------------

            Spacer(Modifier.width(16.dp))

            // --- TEXTOS ---
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = producto.nombre,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 2
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = producto.categoria, // Mostramos la categoría
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "$ ${producto.precio}",
                    color = Color(0xFF00FF88), // Precio en verde neón
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            // --- BOTÓN AGREGAR ---
            IconButton(
                onClick = {
                    onAgregar()
                    isAdded = true
                },
                modifier = Modifier
                    .scale(scale)
                    .background(
                        color = if (isAdded) Color(0xFF00FF88) else Color.Transparent,
                        shape = RoundedCornerShape(50)
                    )
            ) {
                Icon(
                    imageVector = if (isAdded) Icons.Default.Check else Icons.Default.ShoppingCart,
                    contentDescription = "Agregar",
                    tint = if (isAdded) Color.Black else Color(0xFF00FF88)
                )
            }

            // Efecto para regresar el botón a la normalidad
            LaunchedEffect(isAdded) {
                if (isAdded) {
                    delay(500)
                    isAdded = false
                }
            }
        }
    }
}