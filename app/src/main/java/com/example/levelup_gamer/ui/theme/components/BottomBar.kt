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
    carritoViewModel: CarritoViewModel = viewModel()
) {
    // ✅ CAMBIO AQUÍ: Agregamos "Ofertas" a la lista
    val items = listOf(
        BottomNavItem("Inicio", "home", Icons.Default.Home),
        BottomNavItem("Catálogo", "catalogo", Icons.Default.Storefront),
        BottomNavItem("Ofertas", "ofertas", Icons.Default.Star), // <--- NUEVO ÍTEM
        BottomNavItem("Carrito", "carrito", Icons.Default.ShoppingCart),
        BottomNavItem("Perfil", "perfil", Icons.Default.Person)
    )

    NavigationBar(
        containerColor = Color(0xFF0A0A0A),
        tonalElevation = 8.dp
    ) {
        val uiState by carritoViewModel.uiState.collectAsState()
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

                        // Badge del carrito
                        if (item.route == "carrito" && totalItems > 0) {
                            Box(
                                modifier = Modifier
                                    .scale(scale)
                                    .offset(x = 6.dp, y = (-2).dp)
                                    .background(Color(0xFF00FF88), MaterialTheme.shapes.small)
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