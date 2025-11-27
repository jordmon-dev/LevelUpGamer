package com.example.levelup_gamer.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReclamoViewModel : ViewModel() {

    // Estados para el formulario
    private val _fotoUri = MutableStateFlow<Uri?>(null)
    val fotoUri: StateFlow<Uri?> = _fotoUri.asStateFlow()

    private val _descripcion = MutableStateFlow("")
    val descripcion: StateFlow<String> = _descripcion.asStateFlow()

    private val _latitud = MutableStateFlow<Double?>(null)
    val latitud: StateFlow<Double?> = _latitud.asStateFlow()

    private val _longitud = MutableStateFlow<Double?>(null)
    val longitud: StateFlow<Double?> = _longitud.asStateFlow()

    private val _formularioCompleto = MutableStateFlow(false)
    val formularioCompleto: StateFlow<Boolean> = _formularioCompleto.asStateFlow()

    // Funciones para actualizar los estados
    fun actualizarFoto(uri: Uri?) {
        _fotoUri.value = uri
        validarFormulario()
    }

    fun actualizarDescripcion(texto: String) {
        _descripcion.value = texto
        validarFormulario()
    }

    fun actualizarUbicacion(lat: Double, lon: Double) {
        _latitud.value = lat
        _longitud.value = lon
        validarFormulario()
    }

    fun limpiarUbicacion() {
        _latitud.value = null
        _longitud.value = null
        validarFormulario()
    }

    // Validar si el formulario está completo
    private fun validarFormulario() {
        // Validación original (Estricta - Comentada)
        // val fotoValida = _fotoUri.value != null
        val descripcionValida = _descripcion.value.isNotBlank()
        // val ubicacionValida = _latitud.value != null && _longitud.value != null

        // NUEVA VALIDACIÓN: Solo la descripción es obligatoria.
        // La foto y el GPS son opcionales.
        _formularioCompleto.value = descripcionValida
    }

    // Función para enviar el reclamo
    fun enviarReclamo(): Boolean {
        return if (_formularioCompleto.value) {
            // Aquí iría la lógica para enviar el reclamo al servidor
            viewModelScope.launch {
                // Simular envío
                kotlinx.coroutines.delay(1000)
            }
            true
        } else {
            false
        }
    }

    // Limpiar todo el formulario
    fun limpiarFormulario() {
        _fotoUri.value = null
        _descripcion.value = ""
        _latitud.value = null
        _longitud.value = null
        _formularioCompleto.value = false
    }
}