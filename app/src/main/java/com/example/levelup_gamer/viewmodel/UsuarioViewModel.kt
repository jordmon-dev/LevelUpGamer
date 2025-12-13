package com.example.levelup_gamer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.datastore.UserPreferences
import com.example.levelup_gamer.modelo.UsuarioErrores
import com.example.levelup_gamer.modelo.UsuarioState
import com.example.levelup_gamer.repository.UsuarioRepository
import com.example.levelup_gamer.remote.UsuarioInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = UserPreferences(application)
    private val repository = UsuarioRepository(application)

    // Estado del usuario
    private val _usuarioState = MutableStateFlow(UsuarioState())
    val usuarioState: StateFlow<UsuarioState> = _usuarioState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    init {
        cargarDatosGuardados()
        UsuarioInstance.initialize(application)
    }

    // ------------------------------
    // AUTENTICACIÓN CON API
    // ------------------------------
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = repository.login(email, password)

            result.onSuccess { usuario ->
                _loginSuccess.value = true
                // Actualizar estado con datos del usuario
                _usuarioState.value = _usuarioState.value.copy(
                    nombre = usuario.nombre,
                    email = usuario.email,
                    fechaRegistro = usuario.fechaRegistro,
                    puntosFidelidad = usuario.puntosFidelidad
                )

                // Guardar sesión local
                guardarSesionLocal(usuario.nombre, password)
            }.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Error desconocido"
                _loginSuccess.value = false
            }

            _isLoading.value = false
        }
    }

    fun register(nombre: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = repository.register(nombre, email, password)

            result.onSuccess { usuario ->
                _loginSuccess.value = true
                // Actualizar estado con datos del usuario
                _usuarioState.value = _usuarioState.value.copy(
                    nombre = usuario.nombre,
                    email = usuario.email,
                    fechaRegistro = usuario.fechaRegistro,
                    puntosFidelidad = usuario.puntosFidelidad
                )

                // Guardar sesión local
                guardarSesionLocal(usuario.nombre, password)
            }.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Error desconocido"
                _loginSuccess.value = false
            }

            _isLoading.value = false
        }
    }

    fun cargarPerfil() {
        viewModelScope.launch {
            _isLoading.value = true

            val result = repository.getPerfil()

            result.onSuccess { usuario ->
                // Actualizar estado con datos del perfil
                _usuarioState.value = _usuarioState.value.copy(
                    nombre = usuario.nombre,
                    email = usuario.email,
                    fechaRegistro = usuario.fechaRegistro,
                    puntosFidelidad = usuario.puntosFidelidad
                )
            }.onFailure { exception ->
                _errorMessage.value = "Error al cargar perfil: ${exception.message}"
            }

            _isLoading.value = false
        }
    }

    // ------------------------------
    // CARGAR DATOS GUARDADOS (AUTOLOGIN)
    // ------------------------------
    private fun cargarDatosGuardados() {
        viewModelScope.launch {
            val logged = prefs.isLogged.first()

            if (logged && UsuarioInstance.isAuthenticated()) {
                val savedUser = prefs.usuario.first()
                val savedPass = prefs.password.first()

                _usuarioState.value = _usuarioState.value.copy(
                    nombre = savedUser,
                    email = UsuarioInstance.getUserEmail() ?: "",
                    aceptarTerminos = true
                )

                // Intentar auto-login
                login(savedUser, savedPass)
            }
        }
    }

    // ------------------------------
    // VALIDAR LOGIN LOCAL
    // ------------------------------
    fun validar(): Boolean {
        val email = _usuarioState.value.email
        val pass = _usuarioState.value.password
        val acepta = _usuarioState.value.aceptarTerminos

        var valido = true
        val errores = UsuarioErrores()

        if (email.isBlank()) {
            errores.email = "Debe ingresar un email."
            valido = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errores.email = "Email inválido."
            valido = false
        }

        if (pass.isBlank()) {
            errores.password = "Debe ingresar una contraseña."
            valido = false
        } else if (pass.length < 6) {
            errores.password = "La contraseña debe tener al menos 6 caracteres."
            valido = false
        }

        if (!acepta) {
            errores.aceptaTerminos = "Debe aceptar los términos."
            valido = false
        }

        _usuarioState.value = _usuarioState.value.copy(errores = errores)
        return valido
    }

    // ------------------------------
    // VALIDAR REGISTRO
    // ------------------------------
    fun validarRegistro(): Boolean {
        val estado = _usuarioState.value
        var valido = true
        val errores = UsuarioErrores()

        if (estado.nombre.isBlank()) {
            errores.nombre = "Debe ingresar un nombre."
            valido = false
        }

        if (estado.email.isBlank()) {
            errores.email = "Debe ingresar un correo."
            valido = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(estado.email).matches()) {
            errores.email = "Email inválido."
            valido = false
        }

        if (estado.password.isBlank()) {
            errores.password = "Debe ingresar una contraseña."
            valido = false
        } else if (estado.password.length < 6) {
            errores.password = "La contraseña debe tener al menos 6 caracteres."
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

        _usuarioState.value = _usuarioState.value.copy(errores = errores)
        return valido
    }

    // ------------------------------
    // GUARDAR SESIÓN LOCAL (actualizada)
    // ------------------------------
    private fun guardarSesionLocal(nombre: String, password: String) {
        viewModelScope.launch {
            prefs.saveCredentials(nombre, password)
        }
    }

    // ------------------------------
    // LOGOUT
    // ------------------------------
    fun cerrarSesion() {
        viewModelScope.launch {
            prefs.logout()
            repository.logout()
            UsuarioInstance.logout(getApplication())
            _usuarioState.value = UsuarioState()
            _loginSuccess.value = false
        }
    }

    // ------------------------------
    // HANDLERS PARA CAMPOS (mantener igual)
    // ------------------------------
    fun onChangeNombre(value: String) {
        _usuarioState.value = _usuarioState.value.copy(
            nombre = value,
            errores = _usuarioState.value.errores.copy(nombre = "") // Cadena vacía, no null
        )
    }

    fun onChangeEmail(value: String) {
        _usuarioState.value = _usuarioState.value.copy(
            email = value,
            errores = _usuarioState.value.errores.copy(email = "") // Cadena vacía, no null
        )
    }

    fun onChangePassword(value: String) {
        _usuarioState.value = _usuarioState.value.copy(
            password = value,
            errores = _usuarioState.value.errores.copy(password = "") // Cadena vacía, no null
        )
    }

    fun onChangeConfirmPassword(value: String) {
        _usuarioState.value = _usuarioState.value.copy(
            confirmPassword = value,
            errores = _usuarioState.value.errores.copy(confirmPassword = "") // Cadena vacía, no null
        )
    }

    fun onChangeAceptarTerminos(value: Boolean) {
        _usuarioState.value = _usuarioState.value.copy(
            aceptarTerminos = value,
            errores = _usuarioState.value.errores.copy(aceptaTerminos = "") // Cadena vacía, no null
        )
    }
}
