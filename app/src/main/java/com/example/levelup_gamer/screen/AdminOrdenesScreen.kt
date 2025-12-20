package com.example.levelup_gamer.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelup_gamer.model.Orden
import com.example.levelup_gamer.viewmodel.OrdenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminOrdenesScreen(
    navController: NavController,
    viewModel: OrdenViewModel
) {
    val ordenes by viewModel.ordenes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Cargar datos al entrar
    LaunchedEffect(Unit) {
        viewModel.cargarOrdenes()
    }

    val brush = Brush.verticalGradient(
        colors = listOf(Color(0xFF0A0A0A), Color(0xFF1A1A2E), Color(0xFF16213E))
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Dashboard de Ventas", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.cargarOrdenes() }) {
                        Icon(Icons.Default.Refresh, "Recargar", tint = Color(0xFF00FF88))
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
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF00FF88))
                }
            } else if (error != null) {
                Text("Error: $error", color = Color.Red, modifier = Modifier.padding(16.dp))
            } else {

                // LÓGICA DEL DASHBOARD
                val totalVentas = ordenes.sumOf { it.total }
                val cantidadPedidos = ordenes.size
                val ticketPromedio = if (cantidadPedidos > 0) totalVentas / cantidadPedidos else 0

                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // 1. SECCIÓN DE TARJETAS KPI (RESUMEN)
                    item {
                        Text("Resumen Global", color = Color.Gray, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            // Tarjeta 1: Total
                            KpiCard(
                                titulo = "Ventas",
                                valor = "$ ${totalVentas}",
                                icono = Icons.Default.AttachMoney,
                                colorIcono = Color(0xFFFFD700),
                                modifier = Modifier.weight(1f)
                            )
                            // Tarjeta 2: Pedidos
                            KpiCard(
                                titulo = "Pedidos",
                                valor = "$cantidadPedidos",
                                icono = Icons.Default.ShoppingCart,
                                colorIcono = Color(0xFF00BFFF),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // 2. GRÁFICO DE BARRAS SIMPLE
                    item {
                        if (ordenes.isNotEmpty()) {
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth().height(200.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.BarChart, null, tint = Color(0xFF00FF88))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Tendencia (Últimas 10 órdenes)", color = Color.White, fontWeight = FontWeight.Bold)
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                    // Dibujamos el gráfico
                                    GraficoBarrasSimple(ordenes)
                                }
                            }
                        }
                    }

                    // 3. LISTA DE ÓRDENES
                    item {
                        Text("Historial de Transacciones", color = Color.Gray, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    }

                    items(ordenes.reversed()) { orden -> // Mostramos las más nuevas primero
                        OrdenAdminItem(orden)
                    }
                }
            }
        }
    }
}

// --- COMPONENTES AUXILIARES DEL DASHBOARD ---

@Composable
fun KpiCard(titulo: String, valor: String, icono: ImageVector, colorIcono: Color, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icono, null, tint = colorIcono, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(titulo, color = Color.Gray, fontSize = 12.sp)
            Text(valor, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}

@Composable
fun GraficoBarrasSimple(ordenes: List<Orden>) {
    // Tomamos solo las últimas 10 órdenes para el gráfico
    val ultimasOrdenes = ordenes.takeLast(10)
    val maxMonto = ultimasOrdenes.maxOfOrNull { it.total }?.toFloat() ?: 1f

    Canvas(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        val barWidth = size.width / (ultimasOrdenes.size * 2) // Ancho de barra dinámico
        val spacing = barWidth // Espacio entre barras
        val height = size.height

        ultimasOrdenes.forEachIndexed { index, orden ->
            val barHeight = (orden.total / maxMonto) * height

            // Dibujar Barra
            drawRect(
                color = Color(0xFF00FF88),
                topLeft = Offset(
                    x = (index * (barWidth + spacing)) + spacing, // Posición X
                    y = height - barHeight // Posición Y (desde abajo)
                ),
                size = Size(barWidth, barHeight)
            )
        }
    }
}

@Composable
fun OrdenAdminItem(orden: Orden) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A3E)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    "Orden #${orden.numeroOrden}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Cliente: ${orden.nombreCliente}",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
                Text(
                    "Email: ${orden.emailCliente}",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "$ ${orden.total}",
                    color = Color(0xFF00FF88),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                // Usamos una fecha por defecto si viene nula del backend
                Text(
                    orden.fecha?.take(10) ?: "Reciente",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}