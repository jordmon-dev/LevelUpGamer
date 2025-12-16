package com.example.levelup_gamer.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
                title = { Text("Ventas Globales", color = Color.White, fontWeight = FontWeight.Bold) },
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
            Column(modifier = Modifier.padding(16.dp)) {

                // Resumen de Ventas
                if (ordenes.isNotEmpty()) {
                    val totalVentas = ordenes.sumOf { it.total }
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Ingresos Totales", color = Color.Gray, fontSize = 14.sp)
                            Text(
                                "$ ${totalVentas}",
                                color = Color(0xFFFFD700),
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text("${ordenes.size} transacciones registradas", color = Color(0xFF00FF88), fontSize = 12.sp)
                        }
                    }
                }

                if (isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color(0xFF00FF88))
                    }
                } else if (error != null) {
                    Text("Error: $error", color = Color.Red, modifier = Modifier.padding(16.dp))
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(ordenes) { orden ->
                            OrdenAdminItem(orden)
                        }
                    }
                }
            }
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
                    orden.fecha?.take(10) ?: "Hoy",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}