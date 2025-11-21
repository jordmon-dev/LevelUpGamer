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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.CarritoViewModel
import kotlinx.coroutines.delay

data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun BottomBar( // ✅ Nombre de función corregido
    navController: NavController,
    currentRoute: String?,
    carritoViewModel: CarritoViewModel = viewModel()
) {
    val items = listOf(
        BottomNavItem("Inicio", "home", Icons.Default.Home),
        BottomNavItem("Catálogo", "catalogo", Icons.Default.Storefront),
        BottomNavItem("Carrito", "carrito", Icons.Default.ShoppingCart),
        BottomNavItem("Perfil", "perfil", Icons.Default.Person)
    )

    // 🔥 Mismo gradiente neon del LoginScreen
    val fondo = Brush.verticalGradient(
        listOf(
            Color(0xFF0A0A0A),
            Color(0xFF1A1A2E),
            Color(0xFF16213E)
        )
    )

    // 🛒 Cantidad de productos en carrito
    val resumen by carritoViewModel.resumen.collectAsState()
    val totalItems = resumen.items.sumOf { it.cantidad }

    // ❤️ Animación para cuando cambia la cantidad del carrito
    var animateBadge by remember { mutableStateOf(false) }

    LaunchedEffect(totalItems) {
        if (totalItems > 0) {
            animateBadge = true
            delay(150)
            animateBadge = false
        }
    }

    NavigationBar(
        modifier = Modifier
            .height(70.dp)
            .background(fondo),
        containerColor = Color.Transparent,
        contentColor = Color.White
    ) {
        items.forEach { item ->

            val selected = currentRoute == item.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Box(contentAlignment = Alignment.TopEnd) {

                        // Ícono base
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (selected) Color(0xFF00FF88) else Color(0xFFA0A0A0)
                        )

                        // BADGE SOLO EN CARRITO
                        if (item.route == "carrito" && totalItems > 0) {

                            val scale by animateFloatAsState(
                                targetValue = if (animateBadge) 1.2f else 1f,
                                label = "badgeAnim"
                            )

                            Box(
                                modifier = Modifier
                                    .scale(scale)
                                    .offset(x = 6.dp, y = (-2).dp)
                                    .background(
                                        color = Color(0xFF00FF88),
                                        shape = MaterialTheme.shapes.small
                                    )
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
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