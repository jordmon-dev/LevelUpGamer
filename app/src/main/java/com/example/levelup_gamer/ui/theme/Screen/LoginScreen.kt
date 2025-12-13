package com.example.levelup_gamer.ui.theme.Screen

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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

    // SOLUCIÓN: Usar variables de estado locales en lugar de ViewModel
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var aceptarTerminos by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var terminosError by remember { mutableStateOf<String?>(null) }

    // Gradiente para el fondo
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0A0A0A),
            Color(0xFF1A1A2E),
            Color(0xFF16213E)
        )
    )

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
            // Header mejorado
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
                    .shadow(
                        elevation = 16.dp,
                        shape = RoundedCornerShape(20.dp),
                        clip = true
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1E1E2E).copy(alpha = 0.9f)
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        "Iniciar Sesión",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Campo de Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = null
                        },
                        label = {
                            Text("Email")
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Usuario",
                                tint = Color(0xFF00FF88)
                            )
                        },
                        isError = emailError != null,
                        supportingText = {
                            emailError?.let {
                                Text(
                                    it,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color(0xFF00FF88),
                            focusedIndicatorColor = Color(0xFF00FF88),
                            unfocusedIndicatorColor = Color(0xFF666666),
                            errorIndicatorColor = MaterialTheme.colorScheme.error,
                            focusedLabelColor = Color(0xFF00FF88),
                            unfocusedLabelColor = Color(0xFFCCCCCC)
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de Contraseña
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = null
                        },
                        label = {
                            Text("Contraseña")
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "Contraseña",
                                tint = Color(0xFF00FF88)
                            )
                        },
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = image,
                                    contentDescription = "Toggle visibility",
                                    tint = Color(0xFF00FF88)
                                )
                            }
                        },
                        isError = passwordError != null,
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        supportingText = {
                            passwordError?.let {
                                Text(
                                    it,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color(0xFF00FF88),
                            focusedIndicatorColor = Color(0xFF00FF88),
                            unfocusedIndicatorColor = Color(0xFF666666),
                            errorIndicatorColor = MaterialTheme.colorScheme.error,
                            focusedLabelColor = Color(0xFF00FF88),
                            unfocusedLabelColor = Color(0xFFCCCCCC)
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Checkbox de Términos
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = aceptarTerminos,
                            onCheckedChange = {
                                aceptarTerminos = it
                                terminosError = null
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFF00FF88),
                                checkmarkColor = Color.White
                            )
                        )
                        Text(
                            "Acepto los términos y condiciones",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable {
                                    aceptarTerminos = !aceptarTerminos
                                    terminosError = null
                                }
                        )
                    }

                    // Mostrar error de términos si existe
                    terminosError?.let { error ->
                        Text(
                            error,
                            color = Color(0xFFFF6B6B),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón de Login
                    Button(
                        onClick = {
<<<<<<< HEAD
                            // Validación simple
                            var isValid = true
=======
                            if (viewModel.validar()) {
                                viewModel.guardarSesionLocal()  // NUEVO
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0

                            if (email.isEmpty()) {
                                emailError = "El email es requerido"
                                isValid = false
                            } else if (!email.contains("@")) {
                                emailError = "Email inválido"
                                isValid = false
                            }

                            if (password.isEmpty()) {
                                passwordError = "La contraseña es requerida"
                                isValid = false
                            } else if (password.length < 6) {
                                passwordError = "Mínimo 6 caracteres"
                                isValid = false
                            }

                            if (!aceptarTerminos) {
                                terminosError = "Debes aceptar los términos"
                                isValid = false
                            }

                            if (isValid) {
                                // Aquí iría la lógica real de login
                                Toast.makeText(contexto, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()

                                // Navegar a home
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                Toast.makeText(contexto, "Error: revise los campos.", Toast.LENGTH_LONG).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00FF88),
                            contentColor = Color.Black
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 4.dp
                        )
                    ) {
                        Text(
                            "Iniciar Sesión",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Enlace a Registro
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "¿No tienes una cuenta? ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFA0A0A0)
                        )
                        Text(
                            "Crear Cuenta",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF00FF88),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                navController.navigate(route = "registro")
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Opción de "Olvidé mi contraseña"
                    Text(
                        "¿Olvidaste tu contraseña?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF00FF88),
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                Toast.makeText(contexto, "Función en desarrollo", Toast.LENGTH_SHORT).show()
                            }
                    )
                }
            }
        }
    }
}