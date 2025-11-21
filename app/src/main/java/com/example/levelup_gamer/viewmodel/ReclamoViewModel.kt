package com.example.levelup_gamer.viewmodel

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.io.File

class ReclamoViewModel : ViewModel() {

    // Estados para los datos del reclamo
    private val _fotoUri = MutableStateFlow<Uri?>(null)
    val fotoUri: StateFlow<Uri?> = _fotoUri.asStateFlow()

    private val _descripcion = MutableStateFlow("")
    val descripcion: StateFlow<String> = _descripcion.asStateFlow()

    private val _latitud = MutableStateFlow<Double?>(null)
    val latitud: StateFlow<Double?> = _latitud.asStateFlow()

    private val _longitud = MutableStateFlow<Double?>(null)
    val longitud: StateFlow<Double?> = _longitud.asStateFlow()

    private val _permisoCamara = MutableStateFlow(false)
    val permisoCamara: StateFlow<Boolean> = _permisoCamara.asStateFlow()

    // Estado para formulario completo
    private val _formularioCompleto = MutableStateFlow(false)
    val formularioCompleto: StateFlow<Boolean> = _formularioCompleto.asStateFlow()

    // Variables para la cámara
    private var _fotoFile: File? = null
    private var _uri: Uri? = null

    init {
        // Combinar los flows para verificar si el formulario está completo
        viewModelScope.launch {
            combine(_fotoUri, _descripcion, _latitud, _longitud) { foto, desc, lat, lon ->
                foto != null && desc.isNotBlank() && lat != null && lon != null
            }.collect { completo ->
                _formularioCompleto.value = completo
            }
        }
    }

    // Funciones para la cámara
    fun prepararCamara(context: Context) {
        val tiempo = System.currentTimeMillis()
        _fotoFile = File.createTempFile(
            "reclamo_${tiempo}",
            ".jpg",
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )

        _uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            _fotoFile!!
        )
    }

    fun getUriParaCamara(): Uri? {
        return _uri
    }

    fun actualizarPermisoCamara(concedido: Boolean) {
        _permisoCamara.value = concedido
    }

    fun manejarFotoTomada(success: Boolean) {
        if (success) {
            _fotoUri.value = _uri
        }
    }

    // Funciones para actualizar los datos
    fun guardarFoto(uri: Uri?) {
        _fotoUri.value = uri
    }

    fun actualizarFoto(uri: Uri?) {
        _fotoUri.value = uri
    }

    fun actualizarDescripcion(texto: String) {
        _descripcion.value = texto
    }

    fun actualizarUbicacion(lat: Double, lon: Double) {
        _latitud.value = lat
        _longitud.value = lon
    }

    fun limpiarFormulario() {
        _fotoUri.value = null
        _descripcion.value = ""
        _latitud.value = null
        _longitud.value = null
        _fotoFile = null
        _uri = null
    }

    // Función para enviar el reclamo
    fun enviarReclamo(): Boolean {
        return if (_fotoUri.value != null && _descripcion.value.isNotBlank() &&
            _latitud.value != null && _longitud.value != null) {
            // Aquí iría la lógica para enviar a tu backend
            // Por ahora solo limpiamos el formulario
            limpiarFormulario()
            true
        } else {
            false
        }
    }
}