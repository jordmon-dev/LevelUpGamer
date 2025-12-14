package com.example.levelup_gamer.ui.theme.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.CarritoViewModel

data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun BottomBar(
    navController: NavController,
    currentRoute: String?,
    // Quitamos = viewModel() para usar la instancia compartida si se pasa,
    // pero como BottomBar se usa en MainActivity, aquí está bien dejarlo o
    // mejor aún, obtenerlo dentro si no se pasa explícitamente.
    carritoViewModel: CarritoViewModel = viewModel()
) {
    val items = listOf(
        BottomNavItem("Inicio", "home", Icons.Default.Home),
        BottomNavItem("Catálogo", "catalogo", Icons.Default.Storefront),
        BottomNavItem("Carrito", "carrito", Icons.Default.ShoppingCart),
        BottomNavItem("Perfil", "perfil", Icons.Default.Person)
    )

    NavigationBar(
        containerColor = Color(0xFF0A0A0A), // Color oscuro para el fondo
        tonalElevation = 8.dp
    ) {
        // Observamos el estado del carrito
        val uiState by carritoViewModel.uiState.collectAsState()

        // CORRECCIÓN: Calculamos el total sumando las cantidades de la lista de items
        val totalItems = uiState.items.sumOf { it.cantidad }

        items.forEach { item ->
            val selected = currentRoute == item.route
            val scale by animateFloatAsState(if (selected) 1.1f else 1.0f, label = "scale")

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo("home") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Box(contentAlignment = Alignment.TopEnd) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.scale(scale),
                            tint = if (selected) Color(0xFF00FF88) else Color(0xFFA0A0A0)
                        )

                        // Badge de notificación para el Carrito
                        if (item.route == "carrito" && totalItems > 0) {
                            Box(
                                modifier = Modifier
                                    .scale(scale)
                                    .offset(x = 6.dp, y = (-2).dp)
                                    .background(
                                        color = Color(0xFF00FF88),
                                        shape = MaterialTheme.shapes.small
                                    )
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = totalItems.toString(),
                                    color = Color.Black,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (selected) Color(0xFF00FF88) else Color(0xFFA0A0A0)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF00FF88),
                    selectedTextColor = Color(0xFF00FF88),
                    indicatorColor = Color(0xFF00FF88).copy(alpha = 0.15f)
                )
            )
        }
    }
}