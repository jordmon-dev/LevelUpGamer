package com.example.levelup_gamer.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
// 👇 IMPORTANTE: Esta librería es necesaria para los asteriscos (CONTRASEÑA)
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {
    val usuarioState = usuarioViewModel.usuarioState.collectAsState().value

    val fondo = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF050510),
            Color(0xFF0B1020),
            Color(0xFF11162B),
            Color(0xFF050510)
        )
    )

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Registro", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color(0xFF00E5FF)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->

        Box(
            Modifier
                .fillMaxSize()
                .background(fondo)
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    "Crea tu cuenta",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall
                )

                // ------------------- NOMBRE -------------------
                OutlinedTextField(
                    value = usuarioState.nombre,
                    onValueChange = usuarioViewModel::onChangeNombre,
                    label = { Text("Nombre de usuario") },
                    isError = usuarioState.errores.nombre.isNotEmpty(),
                    supportingText = {
                        if (usuarioState.errores.nombre.isNotEmpty()) {
                            Text(text = usuarioState.errores.nombre, color = Color.Red)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // ------------------- EMAIL -------------------
                OutlinedTextField(
                    value = usuarioState.email,
                    onValueChange = usuarioViewModel::onChangeEmail,
                    label = { Text("Correo electrónico") },
                    isError = usuarioState.errores.email.isNotEmpty(),
                    supportingText = {
                        if (usuarioState.errores.email.isNotEmpty()) {
                            Text(text = usuarioState.errores.email, color = Color.Red)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // ------------------- PASSWORD -------------------
                // ✅ CAMBIO 1: Agregamos visualTransformation para ocultar texto
                OutlinedTextField(
                    value = usuarioState.password,
                    onValueChange = usuarioViewModel::onChangePassword,
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(), // <--- ESTO PONE LOS ASTERISCOS
                    isError = usuarioState.errores.password.isNotEmpty(),
                    supportingText = {
                        if (usuarioState.errores.password.isNotEmpty()) {
                            Text(text = usuarioState.errores.password, color = Color.Red)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                // ------------------- CONFIRMAR PASSWORD -------------------
                // ✅ CAMBIO 2: Lo mismo aquí para la confirmación
                OutlinedTextField(
                    value = usuarioState.confirmPassword,
                    onValueChange = usuarioViewModel::onChangeConfirmPassword,
                    label = { Text("Confirmar Contraseña") },
                    visualTransformation = PasswordVisualTransformation(), // <--- ESTO PONE LOS ASTERISCOS
                    isError = usuarioState.errores.confirmPassword.isNotEmpty(),
                    supportingText = {
                        if (usuarioState.errores.confirmPassword.isNotEmpty()) {
                            Text(text = usuarioState.errores.confirmPassword, color = Color.Red)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ------------------- BOTÓN REGISTRAR -------------------
                Button(
                    onClick = {
                        if (usuarioViewModel.validarRegistro()) {
                            usuarioViewModel.register(
                                usuarioState.nombre,
                                usuarioState.email,
                                usuarioState.password
                            )
                            navController.navigate("login")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00FF88),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Registrarme", fontWeight = FontWeight.Bold)
                }

                // Mostrar error general si existe
                val errorMessage = usuarioViewModel.errorMessage.collectAsState().value
                errorMessage?.let { error ->
                    Text(
                        text = error,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}