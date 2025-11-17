package com.example.levelup_gamer.ui.theme.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
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
fun RegistroScreen(
    navController: NavController,
    viewModel: UsuarioViewModel = viewModel()
) {
    val usuario by viewModel.usuario.collectAsState()
    val contexto = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var codigoReferido by remember { mutableStateOf("") }

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
                .verticalScroll(rememberScrollState())
        ) {
            // Header con botón de regreso
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF1E1E2E))
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color(0xFF00FF88)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    "Crear Cuenta",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tarjeta de formulario
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
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
                        "Únete a LEVEL-UP GAMER",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Campo de Nombre completo
                    OutlinedTextField(
                        value = usuario.nombre,
                        onValueChange = viewModel::onChangeNombre,
                        label = { Text("Nombre completo") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Nombre",
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

                    // Campo de Email
                    OutlinedTextField(
                        value = usuario.correo,
                        onValueChange = viewModel::onChangeCorreo,
                        label = { Text("Correo electrónico") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = "Email",
                                tint = Color(0xFF00FF88)
                            )
                        },
                        isError = usuario.errores.correo != null,
                        supportingText = {
                            if (usuario.errores.correo != null) {
                                Text(
                                    usuario.errores.correo!!,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            } else if (usuario.correo.endsWith("@duocuc.cl")) {
                                Text(
                                    "🎓 20% de descuento para estudiantes Duoc",
                                    color = Color(0xFF39FF14),
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

                    // Campo de Edad
                    OutlinedTextField(
                        value = usuario.edad,
                        onValueChange = viewModel::onChangeEdad,
                        label = { Text("Edad") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Edad",
                                tint = Color(0xFF00FF88)
                            )
                        },
                        isError = usuario.errores.edad != null,
                        supportingText = {
                            usuario.errores.edad?.let {
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de Contraseña
                    OutlinedTextField(
                        value = usuario.password,
                        onValueChange = viewModel::onChangePassword,
                        label = { Text("Contraseña") },
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

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de Confirmar Contraseña
                    OutlinedTextField(
                        value = usuario.confirmPassword,
                        onValueChange = viewModel::onChangeConfirmPassword,
                        label = { Text("Confirmar contraseña") },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Lock,
                                contentDescription = "Confirmar contraseña",
                                tint = Color(0xFF00FF88)
                            )
                        },
                        trailingIcon = {
                            val image = if (confirmPasswordVisible)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff

                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Icon(
                                    imageVector = image,
                                    contentDescription = "Toggle visibility",
                                    tint = Color(0xFF00FF88)
                                )
                            }
                        },
                        isError = usuario.errores.confirmPassword != null,
                        visualTransformation = if (confirmPasswordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        supportingText = {
                            usuario.errores.confirmPassword?.let {
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

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de Código de referido (opcional)
                    OutlinedTextField(
                        value = codigoReferido,
                        onValueChange = { codigoReferido = it },
                        label = { Text("Código de referido (opcional)") },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = Color(0xFF00FF88),
                            focusedIndicatorColor = Color(0xFF00FF88),
                            unfocusedIndicatorColor = Color(0xFF666666),
                            focusedLabelColor = Color(0xFF00FF88),
                            unfocusedLabelColor = Color(0xFFCCCCCC)
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Checkbox de Términos y condiciones
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

                    // Botón de Registro
                    Button(
                        onClick = {
                            if (viewModel.validar()) {
                                Toast.makeText(contexto, "¡Cuenta creada exitosamente!", Toast.LENGTH_SHORT).show()
                                navController.navigate("home")
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
                            "Crear Cuenta",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Enlace a Login
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "¿Ya tienes una cuenta? ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFA0A0A0)
                        )
                        Text(
                            "Iniciar Sesión",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF00FF88),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}