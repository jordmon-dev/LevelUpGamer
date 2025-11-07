package com.example.levelup_gamer.ui.theme.Screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: UsuarioViewModel = viewModel()
) {
    val usuario by viewModel.usuario.collectAsState()
    val contexto = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Iniciar Sesión",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(25.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            // Header mejorado
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Bienvenido a",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
                Text(
                    "Level-Up Gamer",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color(0xFF1E90FF)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // ** CAMPO 1: NOMBRE (Usado como Usuario/Identificador) **
            OutlinedTextField(
                value = usuario.nombre,
                onValueChange = viewModel::onChangeNombre,
                label = { Text("Usuario o Email") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Usuario",
                        tint = Color(0xFF1E90FF)
                    )
                },
                isError = usuario.errores.nombre != null,
                supportingText = {
                    usuario.errores.nombre?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                // ❌❌❌ ELIMINA ESTAS LÍNEAS ❌❌❌
                // colors = TextFieldDefaults.outlinedTextFieldColors(
                //     focusedBorderColor = Color(0xFF1E90FF),
                //     unfocusedBorderColor = Color.Gray,
                //     focusedLabelColor = Color(0xFF1E90FF),
                //     cursorColor = Color(0xFF1E90FF)
                // ),
                // ❌❌❌ ELIMINA HASTA AQUÍ ❌❌❌
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(18.dp))

            // ** CAMPO 2: Contraseña **
            OutlinedTextField(
                value = usuario.password,
                onValueChange = viewModel::onChangePassword,
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(
                        Icons.Default.Lock,
                        contentDescription = "Contraseña",
                        tint = Color(0xFF1E90FF)
                    )
                },
                isError = usuario.errores.password != null,
                visualTransformation = PasswordVisualTransformation(),
                supportingText = {
                    usuario.errores.password?.let {
                        Text(it, color = MaterialTheme.colorScheme.error)
                    }
                },
                // ❌❌❌ ELIMINA ESTAS LÍNEAS ❌❌❌
                // colors = TextFieldDefaults.outlinedTextFieldColors(
                //     focusedBorderColor = Color(0xFF1E90FF),
                //     unfocusedBorderColor = Color.Gray,
                //     focusedLabelColor = Color(0xFF1E90FF),
                //     cursorColor = Color(0xFF1E90FF)
                // ),
                // ❌❌❌ ELIMINA HASTA AQUÍ ❌❌❌
                modifier = Modifier.fillMaxWidth()
            )

            // Checkbox de Términos con mejor estilo
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = usuario.aceptarTerminos,
                        onCheckedChange = viewModel::onChangeAceptarTerminos,
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF1E90FF)
                        )
                    )
                    Text(
                        "Acepto los términos y condiciones del acceso al sitio",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            // Mostrar error de términos si existe
            if (usuario.errores.aceptaTerminos != null) {
                Text(
                    usuario.errores.aceptaTerminos!!,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón Aceptar/Login
            Button(
                onClick = {
                    if (viewModel.validar()) {
                        navController.navigate(route = "home")
                    } else {
                        Toast.makeText(contexto, "Error: revise los campos.", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E90FF)
                )
            ) {
                Text(
                    "Iniciar Sesión",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Enlace a Registro
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1A1A1A))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "¿No tienes una cuenta?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(
                        onClick = {
                            navController.navigate(route = "registro")
                        },
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF39FF14)
                        )
                    ) {
                        Text("Crear Cuenta")
                    }
                }
            }
        }
    }
}