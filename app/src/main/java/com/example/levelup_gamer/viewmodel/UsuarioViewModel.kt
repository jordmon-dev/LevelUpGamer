package com.example.levelup_gamer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.datastore.UserPreferences
import com.example.levelup_gamer.model.UsuarioErrores
import com.example.levelup_gamer.model.UsuarioState
import com.example.levelup_gamer.model.UsuarioPerfil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    // ===============================
    //      DATASTORE
    // ===============================
    private val prefs = UserPreferences(application)

    private val _sesionIniciada = MutableStateFlow(false)
    val sesionIniciada: StateFlow<Boolean> = _sesionIniciada

    init {
        // Cargar si está logueado
        viewModelScope.launch {
            _sesionIniciada.value = prefs.sesionIniciada.first()
        }
    }

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
