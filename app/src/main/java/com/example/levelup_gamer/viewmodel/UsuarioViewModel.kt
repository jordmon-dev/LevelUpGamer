package com.example.levelup_gamer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.datastore.UserPreferences
import com.example.levelup_gamer.model.UsuarioErrores
import com.example.levelup_gamer.model.UsuarioState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = UserPreferences(application)

    // Estado del usuario
    private val _usuario = MutableStateFlow(UsuarioState())
    val usuario: StateFlow<UsuarioState> get() = _usuario

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    init {
        cargarDatosGuardados()
    }

    // ------------------------------
    // CARGAR DATOS GUARDADOS (AUTOLOGIN)
    // ------------------------------
    fun cargarDatosGuardados() {
        viewModelScope.launch {
            val logged = prefs.isLogged.first()

            if (logged) {
                val savedUser = prefs.usuario.first()
                val savedPass = prefs.password.first()

                _usuario.value = usuario.value.copy(
                    nombre = savedUser,
                    password = savedPass,
                    aceptarTerminos = true
                )
            }
        }
    }

    // ------------------------------
    // VALIDAR LOGIN
    // ------------------------------
    fun validar(): Boolean {
        val nombre = usuario.value.nombre
        val pass = usuario.value.password
        val acepta = usuario.value.aceptarTerminos

        var valido = true
        val errores = UsuarioErrores()

        if (nombre.isBlank()) {
            errores.nombre = "Debe ingresar un usuario."
            valido = false
        }

        if (pass.isBlank()) {
            errores.password = "Debe ingresar una contraseña."
            valido = false
        }

        if (!acepta) {
            errores.aceptaTerminos = "Debe aceptar los términos."
            valido = false
        }

        _usuario.value = usuario.value.copy(errores = errores)
        return valido
    }

    // ------------------------------
    // VALIDAR REGISTRO
    // ------------------------------
    fun validarRegistro(): Boolean {
        val estado = usuario.value
        var valido = true
        val errores = UsuarioErrores()

        if (estado.nombre.isBlank()) {
            errores.nombre = "Debe ingresar un usuario."
            valido = false
        }

        if (estado.email.isBlank()) {
            errores.email = "Debe ingresar un correo."
            valido = false
        }

        if (estado.password.isBlank()) {
            errores.password = "Debe ingresar una contraseña."
            valido = false
        }

        if (estado.password != estado.confirmPassword) {
            errores.confirmPassword = "Las contraseñas no coinciden."
            valido = false
        }

        if (!estado.aceptarTerminos) {
            errores.aceptaTerminos = "Debe aceptar los términos."
            valido = false
        }

        _usuario.value = usuario.value.copy(errores = errores)
        return valido
    }

    // ------------------------------
    // GUARDAR SESIÓN
    // ------------------------------
    fun guardarSesion() {
        viewModelScope.launch {
            prefs.saveCredentials(
                usuario.value.nombre,
                usuario.value.password
            )
        }
    }

    // ------------------------------
    // LOGOUT
    // ------------------------------
    fun cerrarSesion() {
        viewModelScope.launch {
            prefs.logout()
        }
        _usuario.value = UsuarioState()
    }

    // ------------------------------
    // HANDLERS PARA CAMPOS
    // ------------------------------
    fun onChangeNombre(value: String) {
        _usuario.value = usuario.value.copy(
            nombre = value,
            errores = usuario.value.errores.copy(nombre = null)
        )
    }

    fun onChangeEmail(value: String) {
        _usuario.value = usuario.value.copy(
            email = value,
            errores = usuario.value.errores.copy(email = null)
        )
    }

    fun onChangePassword(value: String) {
        _usuario.value = usuario.value.copy(
            password = value,
            errores = usuario.value.errores.copy(password = null)
        )
    }

    fun onChangeConfirmPassword(value: String) {
        _usuario.value = usuario.value.copy(
            confirmPassword = value,
            errores = usuario.value.errores.copy(confirmPassword = null)
        )
    }

    fun onChangeAceptarTerminos(value: Boolean) {
        _usuario.value = usuario.value.copy(
            aceptarTerminos = value,
            errores = usuario.value.errores.copy(aceptaTerminos = null)
        )
    }
}
