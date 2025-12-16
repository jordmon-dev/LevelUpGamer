package com.example.levelup_gamer.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelup_gamer.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: UsuarioViewModel = viewModel()
) {
    val contexto = LocalContext.current
    val uiState by viewModel.usuarioState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState() // O usa uiState.errores.login si prefieres

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var aceptarTerminos by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    // Gradiente para el fondo
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0A0A0A),
            Color(0xFF1A1A2E),
            Color(0xFF16213E)
        )
    )

    // Efecto para mostrar errores si falló el login
    LaunchedEffect(errorMessage) {
        errorMessage?.let { error ->
            Toast.makeText(contexto, error, Toast.LENGTH_LONG).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Bienvenido a",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFFA0A0A0),
                    fontSize = 16.sp
                )
                Text(
                    "LEVEL-UP GAMER",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color(0xFF00FF88),
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    letterSpacing = 2.sp
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Tarjeta de formulario
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 16.dp, shape = RoundedCornerShape(20.dp), clip = true),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E).copy(alpha = 0.9f)),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        "Iniciar Sesión",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Campo Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it; viewModel.onChangeEmail(it) },
                        label = { Text("Email") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFF00FF88)) },
                        isError = uiState.errores.email.isNotEmpty(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color(0xFF00FF88),
                            focusedIndicatorColor = Color(0xFF00FF88),
                            focusedLabelColor = Color(0xFF00FF88)
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo Password
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it; viewModel.onChangePassword(it) },
                        label = { Text("Contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFF00FF88)) },
                        trailingIcon = {
                            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, contentDescription = null, tint = Color(0xFF00FF88))
                            }
                        },
                        isError = uiState.errores.password.isNotEmpty(),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color(0xFF00FF88),
                            focusedIndicatorColor = Color(0xFF00FF88),
                            focusedLabelColor = Color(0xFF00FF88)
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Checkbox Términos
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = aceptarTerminos,
                            onCheckedChange = { aceptarTerminos = it; viewModel.onChangeAceptarTerminos(it) },
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF00FF88), checkmarkColor = Color.White)
                        )
                        Text(
                            "Acepto los términos y condiciones",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            modifier = Modifier.padding(start = 8.dp).clickable { aceptarTerminos = !aceptarTerminos; viewModel.onChangeAceptarTerminos(aceptarTerminos) }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón de Login (CORREGIDO)
                    Button(
                        onClick = {
                            if (viewModel.validar()) {
                                // ✅ AQUÍ ESTÁ EL CAMBIO CLAVE:
                                // Pasamos 'contexto' y definimos qué hacer si es exitoso (navegar)
                                viewModel.login(
                                    email = email,
                                    pass = password,
                                    context = contexto,
                                    onSuccess = {
                                        Toast.makeText(contexto, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                                        navController.navigate("home") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    }
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp).clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF88), contentColor = Color.Black),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(24.dp))
                        } else {
                            Text("Iniciar Sesión", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Enlaces de pie de página
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("¿No tienes una cuenta? ", color = Color(0xFFA0A0A0))
                        Text(
                            "Crear Cuenta",
                            color = Color(0xFF00FF88),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { navController.navigate("registro") }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "¿Olvidaste tu contraseña?",
                        color = Color(0xFF00FF88),
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().clickable { navController.navigate("recuperar_password") }
                    )
                }
            }
        }
    }
}