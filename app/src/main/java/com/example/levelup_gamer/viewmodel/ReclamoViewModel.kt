package com.example.levelup_gamer.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ReclamoViewModel : ViewModel() {

    // FOTO TOMADA POR LA CÁMARA
    var fotoUri = mutableStateOf<Uri?>(null)
        private set

    fun guardarFoto(uri: Uri?) {
        fotoUri.value = uri
    }

    // UBICACIÓN GPS
    var latitud = mutableStateOf<Double?>(null)
        private set

    var longitud = mutableStateOf<Double?>(null)
        private set

    fun guardarUbicacion(lat: Double, lon: Double) {
        latitud.value = lat
        longitud.value = lon
    }

    // DESCRIPCIÓN
    var descripcion = mutableStateOf("")
        private set

    fun actualizarDescripcion(texto: String) {
        descripcion.value = texto
    }

    fun limpiar() {
        fotoUri.value = null
        latitud.value = null
        longitud.value = null
        descripcion.value = ""
    }
}
