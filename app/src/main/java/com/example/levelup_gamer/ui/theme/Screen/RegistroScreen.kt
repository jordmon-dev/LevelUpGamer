package com.example.levelup_gamer.ui.theme.Screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: UsuarioViewModel = viewModel()
) {
    // 1. Recoger el estado (UI State) del ViewModel
    val usuario by viewModel.usuario.collectAsState()
    val uiState by viewModel.usuario.collectAsState()
    val errores = uiState.errores

    // NOTA: 'codigoReferido' se deja como estado local si no es esencial para la validación del VM.
    var codigoReferido by remember { mutableStateOf("") }


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
                value = uiState.nombre,
                onValueChange = viewModel::onChangeNombre,
                label = { Text("Nombre completo") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Nombre") },
                isError = errores.nombre != null,
                supportingText = {
                    AnimatedVisibility(visible = errores.nombre != null) {
                        errores.nombre?.let { Text(it, color = Color.Red) }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email
            OutlinedTextField(
                value = uiState.correo,
                onValueChange = viewModel::onChangeCorreo,
                label = { Text("Correo electrónico") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
                isError = errores.correo != null,
                supportingText = {
                    AnimatedVisibility(visible = errores.correo != null || uiState.correo.endsWith("@duocuc.cl")) {
                        if (errores.correo != null) {
                            Text(errores.correo!!, color = Color.Red)
                        } else if (uiState.correo.endsWith("@duocuc.cl")) {
                            Text("🎓 20% de descuento para estudiantes Duoc", color = Color(0xFF39FF14))
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = uiState.edad,
                onValueChange = viewModel::onChangeEdad,
                label = {Text("Edad")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = errores.edad != null,
                supportingText = {
                    AnimatedVisibility(visible = errores.edad != null) {

                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Contraseña
            OutlinedTextField(
                value = uiState.password,
                onValueChange = viewModel::onChangePassword,
                label = { Text("Contraseña") },
                isError = errores.password != null,
                supportingText = {
                    AnimatedVisibility(visible = errores.password != null) {
                        errores.password?.let { Text(it, color = Color.Red) }
                    }
                },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Confirmar Password (AHORA CONECTADO AL VM)

            OutlinedTextField(
                value = usuario.confirmPassword,
                onValueChange = viewModel::onChangeConfirmPassword,
                label = { Text("Confirmar contraseña") },
                isError = errores.confirmPassword != null,
                supportingText = {
                    AnimatedVisibility(visible = errores.confirmPassword != null) {
                        errores.confirmPassword?.let { Text(it, color = Color.Red) }
                    }
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Código de referido (opcional)
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
                    checked = uiState.aceptarTerminos,
                    onCheckedChange = viewModel::onChangeAceptarTerminos
                )
                Text(
                    "Acepto los términos y condiciones",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Muestra error de términos desde el VM con animación
            AnimatedVisibility(visible = errores.aceptaTerminos != null) {
                errores.aceptaTerminos?.let { Text(it, color = Color.Red) }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de registro
            Button(
                onClick = {
                    // La lógica de validación ahora es 100% del VM
                    val vmValido = viewModel.validar()

                    // Si es válido, navega a la ruta string "home"
                    if (vmValido) {
                        navController.navigate("home")
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