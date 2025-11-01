package com.example.levelup_gamer.ui.theme.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
// import com.example.levelup_gamer.viewmodel.AppScreens // <-- ELIMINADO

@Composable
fun Home(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título estilizado
        Text(
            "Bienvenido a",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            "Level-Up-Gamer",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF1E90FF)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Botones de navegación (usando rutas de texto plano)
        NavigationButton(
            text = "📱 Catálogo de Productos",
            onClick = { navController.navigate("catalogo") }
        )
        NavigationButton(
            text = "👤 Mi Perfil",
            onClick = { navController.navigate("perfil") }
        )
        NavigationButton(
            text = "🛒 Carrito de Compras",
            onClick = { navController.navigate("carrito") }
        )
        NavigationButton(
            text = "ℹ️ Acerca de",
            onClick = { navController.navigate("about") }
        )
        // Puedes agregar más botones según los requisitos de tu proyecto

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            onClick = {
                // Navega a la ruta de login
                navController.navigate("login") {
                    // Evita que el usuario pueda volver a Home con el botón 'atrás'
                    popUpTo("login") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar Sesión")
        }
    }
}

@Composable
fun NavigationButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text)
    }
}