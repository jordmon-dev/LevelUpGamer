package com.example.levelup_gamer.ui.theme.Screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
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
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.levelup_gamer.modelo.Producto
import com.example.levelup_gamer.viewmodel.CarritoViewModel
import com.example.levelup_gamer.viewmodel.ProductoViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    navController: NavController,
    productoViewModel: ProductoViewModel,
    carritoViewModel: CarritoViewModel
) {
    val uiState by productoViewModel.uiState.collectAsState()
    val productos by productoViewModel.productosFiltrados.collectAsState()

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
                        "Catálogo Gamer",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
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
        containerColor = Color.Transparent
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(fondo)
        ) {

            // Buscador
            OutlinedTextField(
                value = uiState.busqueda,
                onValueChange = productoViewModel::onBusquedaChange,
                placeholder = { Text("Buscar productos...", color = Color(0xFFA0A0A0)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00FF88),
                    unfocusedBorderColor = Color(0xFF444466),
                    cursorColor = Color(0xFF00FF88),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            // Filtros por categoría
            LazyRow(
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(productoViewModel.categorias) { categoria ->
                    FilterChip(
                        selected = uiState.categoriaSeleccionada == categoria,
                        onClick = { productoViewModel.onCategoriaSeleccionada(categoria) },
                        label = {
                            Text(
                                categoria,
                                color = if (uiState.categoriaSeleccionada == categoria)
                                    Color.Black
                                else
                                    Color.White
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Color(0xFF1F1F2E),
                            selectedContainerColor = Color(0xFF00FF88)
                        )
                    )
                }
            }

            // Lista de productos
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(productos) { producto ->

                    ProductoCard(
                        producto = producto,
                        onAgregar = {
                            // Para testing, usa un email fijo o pasa el email real
                            val usuarioEmail = "usuario@ejemplo.com" // Cambia esto
                            carritoViewModel.agregarProducto(
                                productoId = producto.id,
                                cantidad = 1,
                                email = usuarioEmail
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductoCard(
    producto: Producto,
    onAgregar: () -> Unit
) {
    var isAdded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val scale by animateFloatAsState(
        targetValue = if (isAdded) 1.1f else 1.0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "AddButtonScale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E2E)
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // SOLUCIÓN CORREGIDA: Como producto.imagen es Int (resource ID)
            Image(
                painter = painterResource(id = producto.imagen),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {

                Text(
                    producto.nombre,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    "$${producto.precio.toInt()} CLP",
                    color = Color(0xFF00FF88),
                    style = MaterialTheme.typography.bodyLarge
                )

                // Mostrar categoría si existe
                if (producto.categoria.isNotEmpty()) {
                    Spacer(Modifier.height(2.dp))
                    Text(
                        producto.categoria,
                        color = Color(0xFFA0A0A0),
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                // Mostrar stock si existe
                if (producto.stock > 0) {
                    Spacer(Modifier.height(2.dp))
                    Text(
                        "Stock: ${producto.stock}",
                        color = Color(0xFF00FF88),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }

            FilledTonalButton(
                onClick = {
                    onAgregar()
                    if (!isAdded) {
                        isAdded = true
                        scope.launch {
                            delay(600)
                            isAdded = false
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = if (isAdded) Color(0xFF39FF14) else Color(0xFF00FF88),
                    contentColor = Color.Black
                ),
                modifier = Modifier.scale(if (isAdded) scale else 1f),
                enabled = producto.stock > 0
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isAdded) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Agregado",
                        )
                        Spacer(Modifier.width(4.dp))
                        Text("¡Agregado!")
                    } else {
                        Text("Agregar")
                    }
                }
            }
        }
    }
}