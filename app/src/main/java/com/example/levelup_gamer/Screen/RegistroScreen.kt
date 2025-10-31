package com.example.levelup_gamer.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Registro(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var codigoReferido by remember { mutableStateOf("") }
    var aceptaTerminos by remember { mutableStateOf(false) }

    var errores by remember { mutableStateOf(mapOf<String, String>()) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Crear Cuenta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                "Únete a Level-Up Gamer",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Nombre") },
                isError = errores.containsKey("nombre"),
                supportingText = { errores["nombre"]?.let { Text(it, color = Color.Red) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
                isError = errores.containsKey("email"),
                supportingText = {
                    if (errores.containsKey("email")) {
                        Text(errores["email"]!!, color = Color.Red)
                    } else if (email.endsWith("@duocuc.cl")) {
                        Text("🎓 20% de descuento para estudiantes Duoc", color = Color(0xFF39FF14))
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Edad
            OutlinedTextField(
                value = edad,
                onValueChange = { if (it.all { char -> char.isDigit() }) edad = it },
                label = { Text("Edad") },
                keyboardType = KeyboardType.Number,
                isError = errores.containsKey("edad"),
                supportingText = { errores["edad"]?.let { Text(it, color = Color.Red) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                isError = errores.containsKey("password"),
                supportingText = { errores["password"]?.let { Text(it, color = Color.Red) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Confirmar Password
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar contraseña") },
                isError = errores.containsKey("confirmPassword"),
                supportingText = { errores["confirmPassword"]?.let { Text(it, color = Color.Red) } },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Código de referido
            OutlinedTextField(
                value = codigoReferido,
                onValueChange = { codigoReferido = it },
                label = { Text("Código de referido (opcional)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Términos y condiciones
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = aceptaTerminos,
                    onCheckedChange = { aceptaTerminos = it }
                )
                Text(
                    "Acepto los términos y condiciones",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            if (errores.containsKey("terminos")) {
                Text(errores["terminos"]!!, color = Color.Red)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de registro
            Button(
                onClick = {
                    val nuevosErrores = validarRegistro(
                        nombre, email, edad, password, confirmPassword, aceptaTerminos
                    )
                    errores = nuevosErrores

                    if (nuevosErrores.isEmpty()) {
                        navController.navigate("home_screen")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Crear Cuenta")
            }
        }
    }
}

private fun validarRegistro(
    nombre: String,
    email: String,
    edad: String,
    password: String,
    confirmPassword: String,
    aceptaTerminos: Boolean
): Map<String, String> {
    val errores = mutableMapOf<String, String>()

    if (nombre.isEmpty()) errores["nombre"] = "El nombre es obligatorio"
    if (email.isEmpty()) errores["email"] = "El email es obligatorio"
    if (!email.contains("@")) errores["email"] = "Email no válido"
    if (edad.isEmpty()) errores["edad"] = "La edad es obligatoria"
    if (edad.toIntOrNull() ?: 0 < 18) errores["edad"] = "Debes ser mayor de 18 años"
    if (password.length < 6) errores["password"] = "Mínimo 6 caracteres"
    if (password != confirmPassword) errores["confirmPassword"] = "Las contraseñas no coinciden"
    if (!aceptaTerminos) errores["terminos"] = "Debes aceptar los términos y condiciones"

    return errores
}

