package com.example.levelup_gamer.ui.theme.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

data class BottomNavItem(
    val label: String,
    val route: String,
    val icon: ImageVector
)

@Composable
fun BottomBar(navController: NavController, currentRoute: String?) {

    val items = listOf(
        BottomNavItem("Inicio", "home", Icons.Default.Home),
        BottomNavItem("Catálogo", "catalogo", Icons.Default.Storefront),
        BottomNavItem("Carrito", "carrito", Icons.Default.ShoppingCart),
        BottomNavItem("Perfil", "perfil", Icons.Default.Person)
    )

    NavigationBar(
        containerColor = Color(0xFF1A1A1A),
        contentColor = Color.White
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { navController.navigate(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF1E90FF),
                    selectedTextColor = Color(0xFF1E90FF),
                    indicatorColor = Color(0xFF1E90FF).copy(alpha = 0.2f)
                )
            )
        }
    }
}
