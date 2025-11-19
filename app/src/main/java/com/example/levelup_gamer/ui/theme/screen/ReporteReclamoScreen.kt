package com.example.levelup_gamer.ui.theme.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.levelup_gamer.viewmodel.ReclamoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReporteReclamoScreen(
    navController: NavController,
    reclamoViewModel: ReclamoViewModel
) {
    val fondo = Color(0xFF0A0A0A)

    // 🔹 Aquí recibimos la foto tomada desde la cámara
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val fotoUri = savedStateHandle?.get<String>("fotoUri")

    if (fotoUri != null) {
        reclamoViewModel.guardarFoto(fotoUri)
    }

    val foto = reclamoViewModel.fotoUri.collectAsState().value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Reporte de Reclamo", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF111122)
                )
            )
        }
    ) { padding ->

        Column(
            Modifier
                .fillMaxSize()
                .background(fondo)
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "Sube una foto del producto:",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Imagen tomada o placeholder
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .border(2.dp, Color(0xFF00E5FF), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (foto != null) {
                    Image(
                        painter = rememberAsyncImagePainter(foto),
                        contentDescription = "Foto del reclamo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(16.dp))
                    )
                } else {
                    Text("Sin imagen", color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔹 Botón para abrir la cámara
            Button(
                onClick = { navController.navigate("camaraCaptura") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00E5FF),
                    contentColor = Color.Black
                )
            ) {
                Text("Tomar Foto")
            }

            Spacer(modifier = Modifier.height(40.dp))

            // 🔹 Botón de enviar reclamo
            Button(
                onClick = {
                    navController.navigate("confirmacionReclamo")
                    reclamoViewModel.limpiar()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF2E63),
                    contentColor = Color.White
                )
            ) {
                Text("Enviar Reclamo")
            }
        }
    }
}
