package com.example.levelup_gamer.ui.theme.Screen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    val usuario by viewModel.usuario.collectAsState()
    val contexto = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }

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
                // Icono de la app (opcional - puedes eliminar si no tienes el recurso)
                /*
                Icon(
                    painter = painterResource(id = R.drawable.ic_game_controller),
                    contentDescription = "App Icon",
                    tint = Color(0xFF00FF88),
                    modifier = Modifier.size(80.dp)
                )
                */

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

                    // Campo de Usuario/Email - DEFINITIVO
                    OutlinedTextField(
                        value = usuario.nombre,
                        onValueChange = viewModel::onChangeNombre,
                        label = {
                            Text("Usuario o Email")
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Usuario",
                                tint = Color(0xFF00FF88)
                            )
                        },
                        isError = usuario.errores.nombre != null,
                        supportingText = {
                            usuario.errores.nombre?.let {
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

// Campo de Contraseña - DEFINITIVO
                    OutlinedTextField(
                        value = usuario.password,
                        onValueChange = viewModel::onChangePassword,
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
                        isError = usuario.errores.password != null,
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        supportingText = {
                            usuario.errores.password?.let {
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

                    // Checkbox de Términos mejorado
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = usuario.aceptarTerminos,
                            onCheckedChange = viewModel::onChangeAceptarTerminos,
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
                                    viewModel.onChangeAceptarTerminos(!usuario.aceptarTerminos)
                                }
                        )
                    }

                    // Mostrar error de términos si existe
                    if (usuario.errores.aceptaTerminos != null) {
                        Text(
                            usuario.errores.aceptaTerminos!!,
                            color = Color(0xFFFF6B6B),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón de Login mejorado
                    Button(
                        onClick = {
                            if (viewModel.validar()) {
                                Toast.makeText(contexto, "¡Inicio de sesión exitoso!", Toast.LENGTH_SHORT).show()
                                navController.navigate(route = "home")
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

                    // Enlace a Registro mejorado
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
                                // Navegar a pantalla de recuperación
                                Toast.makeText(contexto, "Función en desarrollo", Toast.LENGTH_SHORT).show()
                            }
                    )
                }
            }
        }
    }
}