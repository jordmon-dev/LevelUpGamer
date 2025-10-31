package com.example.levelup_gamer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.levelup_gamer.ui.theme.theme.LevelUpGamerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LevelUpGamerTheme {
                LevelUpGamerApp()
            }
        }
    }
}

@Composable
fun LevelUpGamerApp() {
    val pantallaActual = remember { mutableStateOf("login") }

    when (pantallaActual.value) {
        "login" -> LoginScreen {
            pantallaActual.value = "home"
        }
        "home" -> HomeScreen(
            onIrACatalogo = { pantallaActual.value = "catalogo" },
            onIrAPerfil = { pantallaActual.value = "perfil" },
            onIrAAcercaDe = { pantallaActual.value = "acerca" },
            onIrACarrito = { pantallaActual.value = "carrito" },
            onCerrarSesion = { pantallaActual.value = "login" }
        )
        "catalogo" -> CatalogoScreen(
            onVolver = { pantallaActual.value = "home" },
            onIrACarrito = { pantallaActual.value = "carrito" }
        )
        "perfil" -> PerfilScreen(
            onVolver = { pantallaActual.value = "home" },
            onCerrarSesion = { pantallaActual.value = "login" }
        )
        "acerca" -> AboutScreen { pantallaActual.value = "home" }
        "carrito" -> CarritoScreen(
            onVolver = { pantallaActual.value = "home" },
            onIrACatalogo = { pantallaActual.value = "catalogo" }
        )
    }
}

// Pantalla de Login
@Composable
fun LoginScreen(onLoginExitoso: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "🎮 Level-Up Gamer",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF1E90FF),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    onLoginExitoso()
                } else {
                    errorMessage = "Complete todos los campos"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }
    }
}

// Pantalla de Home
@Composable
fun HomeScreen(
    onIrACatalogo: () -> Unit,
    onIrAPerfil: () -> Unit,
    onIrAAcercaDe: () -> Unit,
    onIrACarrito: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Bienvenido a",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            "Level-Up Gamer",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF1E90FF)
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Botones de navegación
        NavigationButton("📱 Catálogo", onClick = onIrACatalogo)
        NavigationButton("👤 Perfil", onClick = onIrAPerfil)
        NavigationButton("🛒 Carrito", onClick = onIrACarrito)
        NavigationButton("ℹ️ Acerca de", onClick = onIrAAcercaDe)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(onClick = onCerrarSesion) {
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

// Pantalla de Catálogo
@Composable
fun CatalogoScreen(onVolver: () -> Unit, onIrACarrito: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Button(onClick = onVolver) {
            Text("← Volver al Inicio")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("📱 Catálogo de Productos", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        // Productos simples
        ProductoItem("PlayStation 5", "$549.990")
        ProductoItem("Controlador Xbox", "$59.990")
        ProductoItem("Silla Gamer", "$349.990")
        ProductoItem("Mouse Gamer", "$49.990")

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onIrACarrito,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ver Carrito")
        }
    }
}

@Composable
fun ProductoItem(nombre: String, precio: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(nombre, color = Color.White)
            Text(precio, color = Color(0xFF1E90FF))
        }
    }
}

// Pantalla de Perfil
@Composable
fun PerfilScreen(onVolver: () -> Unit, onCerrarSesion: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Button(onClick = onVolver) {
            Text("← Volver al Inicio")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("👤 Mi Perfil", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Nombre: Juan Pérez", color = Color.White)
                Text("Email: juan@duocuc.cl", color = Color.White)
                Text("Puntos LevelUp: 1500", color = Color(0xFF39FF14))
                Text("Nivel: Gamer Pro", color = Color(0xFF1E90FF))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onCerrarSesion,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar Sesión")
        }
    }
}

// Pantalla Acerca de
@Composable
fun AboutScreen(onVolver: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Button(onClick = onVolver) {
            Text("← Volver al Inicio")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("🎮 Level-Up Gamer", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Misión", style = MaterialTheme.typography.titleMedium, color = Color(0xFF39FF14))
                Text("Proporcionar productos de alta calidad para gamers en todo Chile...", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Visión", style = MaterialTheme.typography.titleMedium, color = Color(0xFF39FF14))
                Text("Ser la tienda online líder en productos para gamers en Chile...", color = Color.White)
            }
        }
    }
}

// Pantalla de Carrito
@Composable
fun CarritoScreen(onVolver: () -> Unit, onIrACatalogo: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Button(onClick = onVolver) {
            Text("← Volver al Inicio")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("🛒 Mi Carrito", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        // Mensaje carrito vacío
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Tu carrito está vacío",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Agrega algunos productos desde el catálogo",
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onIrACatalogo) {
                Text("Explorar Catálogo")
            }
        }
    }
}