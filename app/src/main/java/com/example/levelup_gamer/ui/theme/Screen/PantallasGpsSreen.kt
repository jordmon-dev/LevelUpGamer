package com.example.levelup_gamer.ui.theme.Screen

import android.Manifest
import android.R.attr.padding
import android.annotation.SuppressLint
//mport android.graphics.Color
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.navigation.NavController
import androidx.room.util.TableInfo
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import kotlinx.coroutines.tasks.await

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaGps(navController: NavController) {
    val contexto = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(contexto) }

    var userLocation by remember { mutableStateOf<Pair<Double, Double>?>(null) }
    var locationMessage by remember { mutableStateOf("Buscando ubicación...") }

    suspend fun recuperarUbicacionActual() {
        try {
            val location = fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY, null
            ).await()
            if (location != null) {
                userLocation = Pair(location.latitude, location.longitude)
                locationMessage = "Ubicación recuperada correctamente ✅"
            } else {
                locationMessage = "No se pudo obtener la ubicación 😕"
            }
        } catch (e: Exception) {
            locationMessage = "Error: ${e.message}"
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                locationMessage = "Permiso concedido"
            } else {
                locationMessage = "Permiso denegado ❌"
            }
        }
    )

    // Pedir permisos automáticamente al iniciar
    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    // Intentar recuperar la ubicación después de conceder permisos
    LaunchedEffect(locationMessage) {
        if (locationMessage.contains("Permiso concedido", ignoreCase = true)) {
            recuperarUbicacionActual()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("GPS - Mi ubicación") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1A1A1A),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(locationMessage, color = Color.White)
            Spacer(modifier = Modifier.height(20.dp))

            userLocation?.let { (latitude, longitude) ->
                Text("Latitud: $latitude", color = Color(0xFF39FF14))
                Text("Longitud: $longitude", color = Color(0xFF39FF14))
                Spacer(modifier = Modifier.height(16.dp))

                MapboxMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    mapViewportState = rememberMapViewportState {
                        setCameraOptions {
                            zoom(16.0)
                            center(Point.fromLngLat(longitude, latitude))
                            pitch(0.0)
                            bearing(0.0)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    locationPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E90FF)
                )
            ) {
                Text("🔄 Actualizar ubicación")
            }
        }
    }
}

