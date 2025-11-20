// ReclamoViewModel.kt
package com.example.levelup_gamer.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ReclamoViewModel : ViewModel() {
    val fotoUri = mutableStateOf<Uri?>(null)
    val descripcion = mutableStateOf("")
    val latitud = mutableStateOf<Double?>(null)
    val longitud = mutableStateOf<Double?>(null)

    // Cambia el nombre del método a actualizarFoto (o usa guardarFoto si prefieres)
    fun actualizarFoto(uri: Uri?) {
        fotoUri.value = uri
    }

    // O si prefieres mantener el nombre guardarFoto:
    fun guardarFoto(uri: Uri?) {
        fotoUri.value = uri
    }

    fun actualizarDescripcion(texto: String) {
        descripcion.value = texto
    }

    fun actualizarUbicacion(lat: Double, lon: Double) {
        latitud.value = lat
        longitud.value = lon
    }

    fun limpiarDatos() {
        fotoUri.value = null
        descripcion.value = ""
        latitud.value = null
        longitud.value = null
    }
}