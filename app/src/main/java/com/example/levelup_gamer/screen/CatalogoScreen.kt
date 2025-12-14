package com.example.levelup_gamer.screen

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.navigation.NavController
import com.example.levelup_gamer.R
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
    val productos by productoViewModel.productosFiltrados.collectAsState()
    val uiState by productoViewModel.uiState.collectAsState()
    val context = LocalContext.current

    val brush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0A0A0A), Color(0xFF1A1A2E), Color(0xFF16213E))
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Catálogo", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("carrito") }) {
                        Icon(Icons.Default.ShoppingCart, "Carrito", tint = Color(0xFF00FF88))
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().background(brush).padding(padding)) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Buscador
                OutlinedTextField(
                    value = uiState.busqueda,
                    onValueChange = { productoViewModel.onBusquedaChange(it) },
                    placeholder = { Text("Buscar...", color = Color.Gray) },
                    leadingIcon = { Icon(Icons.Default.Search, null, tint = Color(0xFF00FF88)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF1E1E2E),
                        unfocusedContainerColor = Color(0xFF1E1E2E),
                        focusedTextColor = Color.White,
                        focusedIndicatorColor = Color(0xFF00FF88)
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Categorías
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(productoViewModel.categorias) { categoria ->
                        FilterChip(
                            selected = uiState.categoriaSeleccionada == categoria,
                            onClick = { productoViewModel.onCategoriaSeleccionada(categoria) },
                            label = { Text(categoria) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = Color(0xFF00FF88),
                                containerColor = Color(0xFF2A2A3E),
                                labelColor = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Lista
                LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(productos) { producto ->
                        ProductoItem(
                            producto = producto,
                            onAgregar = {
                                carritoViewModel.agregarProducto(
                                    id = producto.id ?: 0,
                                    nombre = producto.nombre,
                                    precio = producto.precio.toInt(),
                                    imagen = producto.imagen ?: R.drawable.game_1
                                )
                                Toast.makeText(context, "Agregado", Toast.LENGTH_SHORT).show()
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

    Card(
        modifier = Modifier.fillMaxWidth().height(120.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E).copy(0.9f))
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = producto.imagen ?: R.drawable.game_1),
                contentDescription = null,
                modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text(producto.nombre, color = Color.White, fontWeight = FontWeight.Bold)
                Text("$ ${producto.precio.toInt()}", color = Color(0xFF00FF88), fontWeight = FontWeight.Bold)
            }
            IconButton(
                onClick = {
                    onAgregar()
                    isAdded = true
                },
                modifier = Modifier.scale(scale)
            ) {
                Icon(if (isAdded) Icons.Default.Check else Icons.Default.ShoppingCart, null, tint = Color(0xFF00FF88))
            }
            LaunchedEffect(isAdded) { if(isAdded) {
                delay(500); isAdded = false } }
        }
    }
}