package com.example.levelup_gamer.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecuperarPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var enviado by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Fondo degradado
    val fondo = Brush.verticalGradient(
        listOf(Color(0xFF0A0A0A), Color(0xFF1A1A2E), Color(0xFF16213E))
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Recuperar Cuenta", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(fondo)
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (enviado) {
                    // --- VISTA DE ÉXITO ---
                    Icon(
                        androidx.compose.material.icons.Icons.Default.Email,
                        contentDescription = null,
                        tint = Color(0xFF00FF88),
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(Modifier.height(24.dp))
                    Text(
                        "¡Correo Enviado!",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Revisa tu bandeja de entrada en $email. Hemos enviado las instrucciones para restablecer tu contraseña.",
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(32.dp))
                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF88), contentColor = Color.Black),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Volver al Login")
                    }

                } else {
                    // --- FORMULARIO ---
                    Text(
                        "¿Olvidaste tu contraseña?",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Ingresa tu correo electrónico y te enviaremos un enlace para recuperarla.",
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(32.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo electrónico") },
                        leadingIcon = { Icon(Icons.Default.Email, null, tint = Color(0xFF00FF88)) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF00FF88),
                            unfocusedBorderColor = Color.Gray,
                            cursorColor = Color(0xFF00FF88),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedLabelColor = Color(0xFF00FF88),
                            unfocusedLabelColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(Modifier.height(24.dp))

                    Button(
                        onClick = {
                            if (email.contains("@")) {
                                scope.launch {
                                    isLoading = true
                                    // Simulamos espera de red
                                    delay(2000)
                                    isLoading = false
                                    enviado = true
                                }
                            } else {
                                Toast.makeText(context, "Ingresa un correo válido", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00FF88),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(24.dp))
                        } else {
                            Text("Enviar Instrucciones", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}