package com.example.levelup_gamer.ui.theme.Screen


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel // <-- Necesario para la inyección
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.UsuarioViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    // ViewModel inyectado correctamente, con acceso al ciclo de vida
    viewModel: UsuarioViewModel = viewModel()
){
    val usuario by viewModel.usuario.collectAsState()
    val contexto = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))

        // ** CAMPO 1: NOMBRE (Usado como Usuario/Identificador) **
        OutlinedTextField(
            value = usuario.nombre,
            onValueChange = viewModel::onChangeNombre,
            label = { Text("Usuario o Email") },
            isError = usuario.errores.nombre != null,
            supportingText = {
                usuario.errores.nombre?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(18.dp))

        // ** CAMPO 2: Contraseña **
        OutlinedTextField(
            value = usuario.password,
            onValueChange = viewModel::onChangePassword,
            label = { Text("Contraseña") },
            isError = usuario.errores.password != null,
            visualTransformation = PasswordVisualTransformation(),
            supportingText = {
                usuario.errores.password?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Checkbox de Términos (De tu código original)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = usuario.aceptarTerminos,
                onCheckedChange = viewModel::onChangeAceptarTerminos
            )
            Text("Acepta los terminos del acceso al sitio")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Aceptar/Login
        Button(
            onClick = {
                if (viewModel.validar()){
                    // Navegación a "bienvenida" usando el string de ruta
                    navController.navigate(route = "bienvenida")
                } else {
                    Toast.makeText(contexto, "Error: revise los campos.", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Aceptar")
        }

        // Enlace a Registro
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(
                onClick = {
                    // Navegación a "registro" usando el string de ruta
                    viewModel.limpiarUsuario() // Limpia el estado antes de ir al registro
                    navController.navigate(route = "registro")
                }
            ) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }
    }

}