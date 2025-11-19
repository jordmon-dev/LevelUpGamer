package com.example.levelup_gamer.ui.theme.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelup_gamer.R
import com.example.levelup_gamer.model.Producto
import com.example.levelup_gamer.viewmodel.CarritoViewModel
import com.example.levelup_gamer.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    navController: NavController,
    viewModel: ProductoViewModel,
    carritoViewModel: CarritoViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val productos by viewModel.productosFiltrados.collectAsState()

    val fondo = Brush.verticalGradient(
        listOf(
            Color(0xFF0A0A0A),
            Color(0xFF111122),
            Color(0xFF1A1A2E),
            Color(0xFF0D0D18)
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Catálogo Gamer", color = Color.White) },
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
                    containerColor = Color.Transparent
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

            // 🔍 BUSCADOR
            OutlinedTextField(
                value = uiState.busqueda,
                onValueChange = viewModel::onBusquedaChange,
                placeholder = { Text("Buscar productos...", color = Color.LightGray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF00E5FF),
                    unfocusedBorderColor = Color(0xFF444466),
                    cursorColor = Color(0xFF00E5FF),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            // 🔥 FILTROS POR CATEGORÍA
            LazyRow(
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.categorias) { categoria ->
                    FilterChip(
                        selected = uiState.categoriaSeleccionada == categoria,
                        onClick = { viewModel.onCategoriaSeleccionada(categoria) },
                        label = { Text(categoria, color = Color.White) },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Color(0xFF1F1F2E),
                            selectedContainerColor = Color(0xFF00E5FF)
                        )
                    )
                }
            }

            // 🛒 LISTA DE PRODUCTOS
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(productos) { producto ->

                    ProductoCard(
                        producto = producto,
                        onAgregar = { carritoViewModel.agregarProducto(producto) }
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A26)
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 🖼 IMAGEN
            Image(
                painter = painterResource(id = producto.imagen ?: R.drawable.banner_game),
                contentDescription = "Producto",
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
                    color = Color(0xFF00E5FF),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            FilledTonalButton(
                onClick = onAgregar,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = Color(0xFF00E5FF),
                    contentColor = Color.Black
                )
            ) {
                Text("Agregar")
            }
        }
    }
}
