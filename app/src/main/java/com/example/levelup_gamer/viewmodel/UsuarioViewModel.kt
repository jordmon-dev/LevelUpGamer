package com.example.levelup_gamer.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.datastore.UserPreferences
import com.example.levelup_gamer.model.UsuarioErrores
import com.example.levelup_gamer.model.UsuarioState
import com.example.levelup_gamer.model.UsuarioPerfil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    // ===============================
    //      DATASTORE
    // ===============================
    private val prefs = UserPreferences(application)

    private val _sesionIniciada = MutableStateFlow(false)
    val sesionIniciada: StateFlow<Boolean> = _sesionIniciada

    // ===============================
    //      FOTO DE PERFIL
    // ===============================
    private val _fotoPerfilUri = MutableStateFlow<Uri?>(null)
    val fotoPerfilUri: StateFlow<Uri?> = _fotoPerfilUri.asStateFlow()

    private val _permisoCamara = MutableStateFlow(false)
    val permisoCamara: StateFlow<Boolean> = _permisoCamara.asStateFlow()

    // Variables para la cámara
    private var _fotoFile: File? = null
    private var _uri: Uri? = null

    init {
        // Cargar si está logueado y la foto de perfil
        viewModelScope.launch {
            _sesionIniciada.value = prefs.sesionIniciada.first()
            cargarFotoPerfilGuardada()
        }
    }

    // ===============================
    //      FUNCIONES PARA LA CÁMARA
    // ===============================

    fun prepararCamara(context: Context) {
        val tiempo = System.currentTimeMillis()
        _fotoFile = File.createTempFile(
            "perfil_${tiempo}",
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
            _fotoPerfilUri.value = _uri
            // Guardar automáticamente cuando se toma la foto
            _uri?.let { uri ->
                guardarFotoPerfil(uri.toString())
            }
        }
    }

    fun guardarFotoPerfil(uri: String) {
        viewModelScope.launch {
            prefs.guardarFotoPerfil(uri)
            println("Foto de perfil guardada en DataStore: $uri")
        }
    }

    private fun cargarFotoPerfilGuardada() {
        viewModelScope.launch {
            prefs.fotoPerfil.collect { uriString ->
                if (!uriString.isNullOrEmpty()) {
                    try {
                        _fotoPerfilUri.value = Uri.parse(uriString)
                        println("Foto de perfil cargada desde DataStore: $uriString")
                    } catch (e: Exception) {
                        println("Error al cargar foto de perfil: ${e.message}")
                    }
                } else {
                    println("No hay foto de perfil guardada")
                    _fotoPerfilUri.value = null
                }
            }
        }
    }

    fun limpiarFotoPerfil() {
        viewModelScope.launch {
            _fotoPerfilUri.value = null
            prefs.limpiarFotoPerfil()
            println("Foto de perfil limpiada")
        }
    }

    // ===============================
    //      SESIÓN
    // ===============================

    fun iniciarSesion(email: String, pass: String) {
        viewModelScope.launch {
            prefs.guardarSesion(email, pass)
            _sesionIniciada.value = true
        }
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            prefs.cerrarSesion()
            _sesionIniciada.value = false
            // Opcional: limpiar también la foto al cerrar sesión
            // limpiarFotoPerfil()
        }
    }

    // ===============================
    //      ESTADO DEL FORMULARIO
    // ===============================

    private val _usuario = MutableStateFlow(UsuarioState())
    val usuario: StateFlow<UsuarioState> = _usuario

    fun onChangeNombre(nombre: String) {
        _usuario.update {
            it.copy(
                nombre = nombre,
                errores = it.errores.copy(nombre = null)
            )
        }
    }

    fun onChangeEdad(edad: String) {
        if (edad.all { it.isDigit() } || edad.isEmpty()) {
            _usuario.update {
                it.copy(
                    edad = edad,
                    errores = it.errores.copy(edad = null)
                )
            }
        }
    }

    fun onChangeCorreo(correo: String) {
        _usuario.update {
            it.copy(
                correo = correo,
                errores = it.errores.copy(correo = null)
            )
        }
    }

    fun onChangePassword(pass: String) {
        _usuario.update {
            it.copy(
                password = pass,
                errores = it.errores.copy(password = null)
            )
        }
    }

    fun onChangeConfirmPassword(pass: String) {
        _usuario.update {
            it.copy(
                confirmPassword = pass,
                errores = it.errores.copy(confirmPassword = null)
            )
        }
    }

    fun onChangeAceptarTerminos(valor: Boolean) {
        _usuario.update {
            it.copy(
                aceptarTerminos = valor,
                errores = it.errores.copy(aceptaTerminos = null)
            )
        }
    }

    fun validar(): Boolean {
        val f = _usuario.value

        val edadInt = f.edad.toIntOrNull() ?: 0
        val errorEdad =
            if (f.edad.isBlank() || edadInt < 18) "Debes ser mayor de 18 años" else null

        val errorConfirmPass =
            if (f.password != f.confirmPassword) "Las contraseñas no coinciden" else null

        val errores = UsuarioErrores(
            nombre = if (f.nombre.isBlank()) "El nombre está vacío" else null,
            correo = if (f.correo.isBlank() || !f.correo.contains("@"))
                "Error en el ingreso del correo"
            else null,
            password = when {
                f.password.isBlank() -> "Contraseña vacía"
                f.password.length < 6 -> "Debe tener mínimo 6 caracteres"
                else -> null
            },
            edad = errorEdad,
            confirmPassword = errorConfirmPass,
            aceptaTerminos = if (!f.aceptarTerminos)
                "Debe aceptar los términos"
            else null
        )

        _usuario.update { it.copy(errores = errores) }

        return errores.nombre == null &&
                errores.correo == null &&
                errores.password == null &&
                errores.edad == null &&
                errores.confirmPassword == null &&
                errores.aceptaTerminos == null
    }

    fun limpiarEstado() {
        _usuario.value = UsuarioState()
    }

    fun toUsuarioPerfil(): UsuarioPerfil {
        val f = _usuario.value
        return UsuarioPerfil(
            nombre = f.nombre,
            email = f.correo,
            puntosLevelUp = 1500,
            nivel = if (f.correo.endsWith("@duocuc.cl")) "Gamer Pro Estudiante" else "Gamer",
            fechaRegistro = java.text.SimpleDateFormat(
                "dd/MM/yyyy",
                java.util.Locale.getDefault()
            ).format(java.util.Date())
        )
    }
}