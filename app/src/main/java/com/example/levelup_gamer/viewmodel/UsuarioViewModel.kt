package com.example.levelup_gamer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.datastore.UserPreferences
<<<<<<< HEAD
import com.example.levelup_gamer.modelo.UsuarioErrores
import com.example.levelup_gamer.modelo.UsuarioState
=======
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
import com.example.levelup_gamer.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = UserPreferences(application)
    private val repository = UsuarioRepository()

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
<<<<<<< HEAD
        UsuarioInstance.initialize(application)
=======
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
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
<<<<<<< HEAD
                // Actualizar estado con datos del usuario
                _usuarioState.value = _usuarioState.value.copy(
                    nombre = usuario.nombre,
                    email = usuario.email,
                    fechaRegistro = usuario.fechaRegistro,
                    puntosFidelidad = usuario.puntosFidelidad
                )

                // Guardar sesión local
                guardarSesionLocal(usuario.nombre, password)
=======
                guardarSesionLocal()
                // Cargar perfil después del login
                cargarPerfil(authResponse.email)
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
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
<<<<<<< HEAD
                // Actualizar estado con datos del usuario
                _usuarioState.value = _usuarioState.value.copy(
                    nombre = usuario.nombre,
                    email = usuario.email,
                    fechaRegistro = usuario.fechaRegistro,
                    puntosFidelidad = usuario.puntosFidelidad
                )

                // Guardar sesión local
                guardarSesionLocal(usuario.nombre, password)
=======
                guardarSesionLocal()
                // Cargar perfil después del registro
                cargarPerfil(authResponse.email)
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
            }.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Error desconocido"
                _loginSuccess.value = false
            }

            _isLoading.value = false
        }
    }

<<<<<<< HEAD
    fun cargarPerfil() {
=======
    fun cargarPerfil(email: String) {
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
        viewModelScope.launch {
            _isLoading.value = true

            val result = repository.getPerfil(email)

            result.onSuccess { usuario ->
<<<<<<< HEAD
                // Actualizar estado con datos del perfil
                _usuarioState.value = _usuarioState.value.copy(
                    nombre = usuario.nombre,
                    email = usuario.email,
                    fechaRegistro = usuario.fechaRegistro,
                    puntosFidelidad = usuario.puntosFidelidad
=======
                // Actualizar estado local con datos del perfil
                _usuario.value = _usuario.value.copy(
                    nombre = usuario.nombreCompleto,
                    email = usuario.email
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
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

            if (logged) {
                val savedUser = prefs.usuario.first()
<<<<<<< HEAD
                val savedPass = prefs.password.first()

                _usuarioState.value = _usuarioState.value.copy(
                    nombre = savedUser,
                    email = UsuarioInstance.getUserEmail() ?: "",
                    aceptarTerminos = true
                )

                // Intentar auto-login
                login(savedUser, savedPass)
=======
                // Si el usuario está logueado, intentamos cargar su perfil
                if(savedUser.isNotBlank()){
                    cargarPerfil(savedUser)
                }
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
            }
        }
    }

    // ------------------------------
    // VALIDAR LOGIN LOCAL
    // ------------------------------
    fun validar(): Boolean {
<<<<<<< HEAD
        val email = _usuarioState.value.email
        val pass = _usuarioState.value.password
        val acepta = _usuarioState.value.aceptarTerminos
=======
        val email = usuario.value.email
        val pass = usuario.value.password
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0

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

<<<<<<< HEAD
        if (!acepta) {
            errores.aceptaTerminos = "Debe aceptar los términos."
            valido = false
        }

        _usuarioState.value = _usuarioState.value.copy(errores = errores)
=======
        _usuario.value = usuario.value.copy(errores = errores)
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
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
<<<<<<< HEAD
            prefs.saveCredentials(nombre, password)
=======
            prefs.saveCredentials(
                usuario.value.email,
                usuario.value.password
            )
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
        }
    }

    // ------------------------------
    // LOGOUT
    // ------------------------------
    fun cerrarSesion() {
        viewModelScope.launch {
            repository.logout()
            prefs.logout()
<<<<<<< HEAD
            repository.logout()
            UsuarioInstance.logout(getApplication())
            _usuarioState.value = UsuarioState()
=======
            _usuario.value = UsuarioState()
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
            _loginSuccess.value = false
        }
    }

    // ------------------------------
    // HANDLERS PARA CAMPOS (mantener igual)
    // ------------------------------
    fun onChangeNombre(value: String) {
<<<<<<< HEAD
        _usuarioState.value = _usuarioState.value.copy(
            nombre = value,
            errores = _usuarioState.value.errores.copy(nombre = "") // Cadena vacía, no null
=======
        _usuario.value = _usuario.value.copy(
            nombre = value,
            errores = _usuario.value.errores.copy(nombre = null)
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
        )
    }

    fun onChangeEmail(value: String) {
<<<<<<< HEAD
        _usuarioState.value = _usuarioState.value.copy(
            email = value,
            errores = _usuarioState.value.errores.copy(email = "") // Cadena vacía, no null
=======
        _usuario.value = _usuario.value.copy(
            email = value,
            errores = _usuario.value.errores.copy(email = null)
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
        )
    }

    fun onChangePassword(value: String) {
<<<<<<< HEAD
        _usuarioState.value = _usuarioState.value.copy(
            password = value,
            errores = _usuarioState.value.errores.copy(password = "") // Cadena vacía, no null
=======
        _usuario.value = _usuario.value.copy(
            password = value,
            errores = _usuario.value.errores.copy(password = null)
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
        )
    }

    fun onChangeConfirmPassword(value: String) {
<<<<<<< HEAD
        _usuarioState.value = _usuarioState.value.copy(
            confirmPassword = value,
            errores = _usuarioState.value.errores.copy(confirmPassword = "") // Cadena vacía, no null
=======
        _usuario.value = _usuario.value.copy(
            confirmPassword = value,
            errores = _usuario.value.errores.copy(confirmPassword = null)
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
        )
    }

    fun onChangeAceptarTerminos(value: Boolean) {
<<<<<<< HEAD
        _usuarioState.value = _usuarioState.value.copy(
            aceptarTerminos = value,
            errores = _usuarioState.value.errores.copy(aceptaTerminos = "") // Cadena vacía, no null
=======
        _usuario.value = _usuario.value.copy(
            aceptarTerminos = value,
            errores = _usuario.value.errores.copy(aceptaTerminos = null)
>>>>>>> 26325cd399d3b00d6b44ae2d699d36192856a8d0
        )
    }
}
