package com.example.levelup_gamer.ui.theme.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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

    val foto = reclamoViewModel.fotoUri.value
    val descripcion = reclamoViewModel.descripcion.value
    val lat = reclamoViewModel.latitud.value
    val lon = reclamoViewModel.longitud.value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Reporte de Reclamo") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // FOTO
            Text("Foto del producto", style = MaterialTheme.typography.titleMedium)

            if (foto != null) {
                Image(
                    painter = rememberAsyncImagePainter(model = foto),
                    contentDescription = "Foto",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )
            } else {
                OutlinedButton(
                    onClick = { navController.navigate("camaraCaptura") },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Tomar fotografía") }
            }

            Spacer(Modifier.height(10.dp))

            // DESCRIPCIÓN
            Text("Descripción del problema", style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = descripcion,
                onValueChange = { reclamoViewModel.actualizarDescripcion(it) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            // UBICACIÓN
            Text("Ubicación", style = MaterialTheme.typography.titleMedium)

            if (lat != null && lon != null) {
                Text("Latitud: $lat")
                Text("Longitud: $lon")
            } else {
                OutlinedButton(
                    onClick = { navController.navigate("gps") },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Obtener ubicación actual") }
            }

            Spacer(Modifier.height(20.dp))

            // ENVIAR RECLAMO
            Button(
                onClick = {
                    navController.navigate("confirmacionReclamo")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = foto != null && descripcion.isNotBlank() && lat != null
            ) {
                Text("Enviar Reclamo")
            }
        }
    }
}
