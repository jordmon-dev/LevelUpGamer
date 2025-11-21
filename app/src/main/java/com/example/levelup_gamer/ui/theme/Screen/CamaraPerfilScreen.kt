package com.example.levelup_gamer.viewmodel

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.io.File

class CamaraPerfilViewModel : ViewModel() {

    private val _fotoPerfilUri = MutableStateFlow<Uri?>(null)
    val fotoPerfilUri: StateFlow<Uri?> = _fotoPerfilUri

    private val _permisoCamara = MutableStateFlow(false)
    val permisoCamara: StateFlow<Boolean> = _permisoCamara

    private var archivoFoto: File? = null

    fun prepararCamara(context: Context) {
        archivoFoto = File(
            context.getExternalFilesDir(null),
            "foto_perfil_${System.currentTimeMillis()}.jpg"
        )
        _fotoPerfilUri.value = FileProvider.getUriForFile(
            context,
            context.packageName + ".provider",
            archivoFoto!!
        )
    }

    fun getUriParaCamara(): Uri? = _fotoPerfilUri.value

    fun manejarFotoTomada(success: Boolean) {
        if (!success) _fotoPerfilUri.value = null
    }

    fun actualizarPermisoCamara(isGranted: Boolean) {
        _permisoCamara.value = isGranted
    }
}
