package com.example.levelup_gamer.ui.theme.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.viewmodel.ProductoViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Catalogo(navController: NavController, viewModel: ProductoViewModel) {
    var busqueda by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf("Todas") }

    val categorias = listOf("Todas", "Consolas", "Accesorios", "Juegos de Mesa", "Computadores", "Sillas", "Mouse", "Poleras")

    val productos = listOf(
        Producto("CQ001", "PlayStation 5", 549990.0, "Consolas", "Consola de última generación"),
        Producto("AC001", "Controlador Xbox Series X", 59990.0, "Accesorios", "Control inalámbrico"),
        Producto("JM001", "Catan", 29990.0, "Juegos de Mesa", "Juego de estrategia"),
        Producto("CG001", "PC Gamer ASUS ROG", 1299990.0, "Computadores", "Alto rendimiento"),
        Producto("SG001", "Silla Gamer SecretLab", 349990.0, "Sillas", "Máximo confort"),
        Producto("MS001", "Mouse Logitech G502", 49990.0, "Mouse", "Alta precisión"),
        Producto("PP001", "Polera Gamer Personalizada", 14990.0, "Poleras", "Personalizable")
    )

    val productosFiltrados = productos.filter { producto ->
        (categoriaSeleccionada == "Todas" || producto.categoria == categoriaSeleccionada) &&
                (producto.nombre.contains(busqueda, ignoreCase = true) ||
                        producto.descripcion.contains(busqueda, ignoreCase = true))
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Catálogo de Productos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("carrito_screen") }) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Barra de búsqueda
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                placeholder = { Text("Buscar productos...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )

            // Filtros de categoría
            LazyColumn(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .height(60.dp)
            ) {
                items(categorias) { categoria ->
                    FilterChip(
                        selected = categoriaSeleccionada == categoria,
                        onClick = { categoriaSeleccionada = categoria },
                        label = { Text(categoria) },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Lista de productos
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            ) {
                items(productosFiltrados) { producto ->
                    ProductoItem(
                        producto = producto,
                        onAgregarCarrito = {
                            // Lógica para agregar al carrito
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductoItem(
    producto: Producto,
    onAgregarCarrito: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    producto.nombre,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    producto.categoria,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    producto.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    "$${producto.precio.roundToInt()} CLP",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF1E90FF),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            IconButton(
                onClick = onAgregarCarrito,
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar al carrito", tint = Color(0xFF39FF14))
            }
        }
    }
}
