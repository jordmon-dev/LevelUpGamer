package com.example.levelup_gamer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.datastore.UserPreferences
import com.example.levelup_gamer.model.UsuarioErrores
import com.example.levelup_gamer.model.UsuarioState
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
    private val _usuario = MutableStateFlow(UsuarioState())
    val usuario: StateFlow<UsuarioState> = _usuario.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    init {
        cargarDatosGuardados()
        // Inicializar UsuarioInstance
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

            result.onSuccess { authResponse ->
                _loginSuccess.value = true
                // Cargar perfil después del login
                cargarPerfil()
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

            result.onSuccess { authResponse ->
                _loginSuccess.value = true
                // Cargar perfil después del registro
                cargarPerfil()
            }.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Error desconocido"
                _loginSuccess.value = false
            }

            _isLoading.value = false
        }
    }

    suspend fun cargarPerfil() {
        viewModelScope.launch {
            _isLoading.value = true

            val result = repository.getPerfil()

            result.onSuccess { usuario ->
                // Actualizar estado local con datos del perfil
                _usuario.value = _usuario.value.copy(
                    nombre = usuario.nombre,
                    email = usuario.email,
                    fechaRegistro = usuario.fechaRegistro ?: "",
                    // Puedes agregar más campos según necesites
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

                _usuario.value = usuario.value.copy(
                    nombre = savedUser,
                    email = UsuarioInstance.getUserEmail() ?: "",
                    aceptarTerminos = true
                )

                // Intentar cargar perfil desde API
                cargarPerfil()
            }
        }
    }

    // ------------------------------
    // VALIDAR LOGIN LOCAL
    // ------------------------------
    fun validar(): Boolean {
        val email = usuario.value.email
        val pass = usuario.value.password
        val acepta = usuario.value.aceptarTerminos

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

        _usuario.value = usuario.value.copy(errores = errores)
        return valido
    }

    // ------------------------------
    // GUARDAR SESIÓN LOCAL
    // ------------------------------
    fun guardarSesionLocal() {
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
            UsuarioInstance.logout(getApplication())
            _usuario.value = UsuarioState()
            _loginSuccess.value = false
        }
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