package com.example.levelup_gamer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.datastore.UserPreferences
import com.example.levelup_gamer.model.UsuarioErrores
import com.example.levelup_gamer.model.UsuarioState
import com.example.levelup_gamer.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = UserPreferences(application)

    // ✅ CORRECCIÓN FINAL: Inicializamos el repositorio vacío.
    // Ya no pasamos 'application' porque ahora usamos Retrofit, no base de datos local.
    private val repository = UsuarioRepository()

    // --- Estados de la UI ---
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
    }

    // ------------------------------
    // LOGIN CONECTADO A SPRING BOOT
    // ------------------------------
    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null // Limpiar errores previos

            try {
                // Llamada al repositorio (que llama a Retrofit)
                val response = repository.login(email, pass)

                if (response.isSuccessful && response.body() != null) {
                    val authResponse = response.body()!!
                    val usuarioRecibido = authResponse.usuario
                    val token = authResponse.token

                    _loginSuccess.value = true

                    // Actualizamos la UI con los datos reales del backend
                    _usuarioState.value = _usuarioState.value.copy(
                        nombre = usuarioRecibido?.nombre ?: email, // Si viene nulo, usamos el email
                        email = usuarioRecibido?.email ?: email,
                        password = pass
                    )

                    // Guardamos sesión en el celular para que no tenga que loguear de nuevo
                    guardarSesionLocal(usuarioRecibido?.nombre ?: email, pass)

                } else {
                    // Error 401 o similar
                    _errorMessage.value = "Credenciales incorrectas o usuario no encontrado."
                    _loginSuccess.value = false
                }
            } catch (e: Exception) {
                // Error de conexión (Servidor apagado, sin internet, IP incorrecta)
                _errorMessage.value = "Error de conexión: Verifica que el backend esté corriendo."
                _loginSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ------------------------------
    // REGISTRO CONECTADO A SPRING BOOT
    // ------------------------------
    fun register(nombre: String, email: String, pass: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = repository.registrar(nombre, email, pass)

                if (response.isSuccessful) {
                    // Registro exitoso -> Intentamos hacer login automático
                    _errorMessage.value = "¡Cuenta creada con éxito! Iniciando..."
                    login(email, pass)
                } else {
                    // Error 400 (ej: email ya existe)
                    _errorMessage.value = "No se pudo registrar. Revisa si el correo ya existe."
                    _loginSuccess.value = false
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión al intentar registrarse."
                _loginSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ------------------------------
    // FUNCIONES LOCALES (DataStore y Validaciones)
    // ------------------------------

    private fun guardarSesionLocal(nombre: String, pass: String) {
        viewModelScope.launch {
            prefs.saveCredentials(nombre, pass)
        }
    }

    private fun cargarDatosGuardados() {
        viewModelScope.launch {
            // Verificamos si ya estaba logueado en el celular
            if (prefs.isLogged.first()) {
                val savedUser = prefs.usuario.first()
                val savedPass = prefs.password.first()

                _usuarioState.value = _usuarioState.value.copy(
                    nombre = savedUser,
                    email = savedUser,
                    password = savedPass,
                    aceptarTerminos = true
                )
            }
        }
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            prefs.logout()
            _usuarioState.value = UsuarioState()
            _loginSuccess.value = false
        }
    }

    // --- Validaciones de campos (Se mantienen igual) ---

    fun validar(): Boolean {
        val estado = _usuarioState.value
        var valido = true
        val errores = UsuarioErrores()

        if (estado.email.isBlank()) {
            errores.email = "El email es requerido."
            valido = false
        }
        if (estado.password.isBlank()) {
            errores.password = "La contraseña es requerida."
            valido = false
        }
        if (!estado.aceptarTerminos) {
            errores.aceptaTerminos = "Debes aceptar los términos."
            valido = false
        }

        _usuarioState.value = estado.copy(errores = errores)
        return valido
    }

    fun validarRegistro(): Boolean {
        val estado = _usuarioState.value
        var valido = true
        val errores = UsuarioErrores()

        if (estado.nombre.isBlank()) { errores.nombre = "Nombre requerido"; valido = false }
        if (estado.email.isBlank()) { errores.email = "Email requerido"; valido = false }
        if (estado.password.length < 6) { errores.password = "Mínimo 6 caracteres"; valido = false }
        if (estado.password != estado.confirmPassword) { errores.confirmPassword = "No coinciden"; valido = false }
        if (!estado.aceptarTerminos) { errores.aceptaTerminos = "Requerido"; valido = false }

        _usuarioState.value = estado.copy(errores = errores)
        return valido
    }

    // --- Handlers para inputs (Se mantienen igual) ---
    fun onChangeNombre(v: String) { _usuarioState.value = _usuarioState.value.copy(nombre = v, errores = _usuarioState.value.errores.copy(nombre = "")) }
    fun onChangeEmail(v: String) { _usuarioState.value = _usuarioState.value.copy(email = v, errores = _usuarioState.value.errores.copy(email = "")) }
    fun onChangePassword(v: String) { _usuarioState.value = _usuarioState.value.copy(password = v, errores = _usuarioState.value.errores.copy(password = "")) }
    fun onChangeConfirmPassword(v: String) { _usuarioState.value = _usuarioState.value.copy(confirmPassword = v, errores = _usuarioState.value.errores.copy(confirmPassword = "")) }
    fun onChangeAceptarTerminos(v: Boolean) { _usuarioState.value = _usuarioState.value.copy(aceptarTerminos = v, errores = _usuarioState.value.errores.copy(aceptaTerminos = "")) }
}