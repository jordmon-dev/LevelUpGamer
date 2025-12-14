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

    // 🔴 CORRECCIÓN CLAVE:
    // Antes: private val repository = UsuarioRepository(application) -> ERROR
    // Ahora: Lo iniciamos vacío porque ya usa Retrofit por dentro.
    private val repository = UsuarioRepository()

    // Estados de UI
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
    // LOGIN (Conectado al Backend)
    // ------------------------------
    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                // Llamada a la API
                val response = repository.login(email, pass)

                if (response.isSuccessful && response.body() != null) {
                    val authResponse = response.body()!!
                    val token = authResponse.token
                    val usuario = authResponse.usuario

                    _loginSuccess.value = true

                    // Actualizamos la UI con los datos que llegaron
                    _usuarioState.value = _usuarioState.value.copy(
                        nombre = usuario?.nombre ?: email, // Si el backend no manda nombre, usamos email
                        email = usuario?.email ?: email,
                        password = pass // Guardamos la pass para autologin futuro
                    )

                    // Guardamos la sesión en el celular
                    guardarSesionLocal(usuario?.nombre ?: email, pass)

                } else {
                    // Si el backend dice 401 (No autorizado)
                    _errorMessage.value = "Login fallido: Revisa tu correo o contraseña."
                    _loginSuccess.value = false
                }
            } catch (e: Exception) {
                // Si el servidor está apagado
                _errorMessage.value = "Error de conexión: Verifica que el backend esté corriendo."
                _loginSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ------------------------------
    // REGISTRO (Conectado al Backend)
    // ------------------------------
    fun register(nombre: String, email: String, pass: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                // Llamada a la API
                val response = repository.registrar(nombre, email, pass)

                if (response.isSuccessful) {
                    // Registro OK -> Intentamos loguear automáticamente
                    _errorMessage.value = "¡Cuenta creada! Iniciando sesión..."
                    login(email, pass)
                } else {
                    _errorMessage.value = "Error al registrar. El correo podría estar usado."
                    _loginSuccess.value = false
                }
            } catch (e: Exception) {
                _errorMessage.value = "No se pudo conectar con el servidor."
                _loginSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ------------------------------
    // DATOS LOCALES (DataStore)
    // ------------------------------
    private fun guardarSesionLocal(nombre: String, pass: String) {
        viewModelScope.launch {
            prefs.saveCredentials(nombre, pass)
        }
    }

    private fun cargarDatosGuardados() {
        viewModelScope.launch {
            val logged = prefs.isLogged.first()
            if (logged) {
                val savedUser = prefs.usuario.first()
                val savedPass = prefs.password.first()

                _usuarioState.value = _usuarioState.value.copy(
                    nombre = savedUser,
                    email = savedUser, // Asumimos que el usuario guardado es el email/nombre
                    password = savedPass,
                    aceptarTerminos = true
                )
            }
        }
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            prefs.logout()
            _usuarioState.value = UsuarioState() // Reseteamos UI
            _loginSuccess.value = false
        }
    }

    // ------------------------------
    // VALIDACIONES Y HANDLERS (Igual que antes)
    // ------------------------------

    // Handlers para actualizar el estado mientras escribes
    fun onChangeNombre(v: String) { _usuarioState.value = _usuarioState.value.copy(nombre = v, errores = _usuarioState.value.errores.copy(nombre = "")) }
    fun onChangeEmail(v: String) { _usuarioState.value = _usuarioState.value.copy(email = v, errores = _usuarioState.value.errores.copy(email = "")) }
    fun onChangePassword(v: String) { _usuarioState.value = _usuarioState.value.copy(password = v, errores = _usuarioState.value.errores.copy(password = "")) }
    fun onChangeConfirmPassword(v: String) { _usuarioState.value = _usuarioState.value.copy(confirmPassword = v, errores = _usuarioState.value.errores.copy(confirmPassword = "")) }
    fun onChangeAceptarTerminos(v: Boolean) { _usuarioState.value = _usuarioState.value.copy(aceptarTerminos = v, errores = _usuarioState.value.errores.copy(aceptaTerminos = "")) }

    // Validación Login
    fun validar(): Boolean {
        val s = _usuarioState.value
        var valido = true
        val err = UsuarioErrores()

        if (s.email.isBlank()) { err.email = "Ingresa tu email"; valido = false }
        if (s.password.isBlank()) { err.password = "Ingresa tu contraseña"; valido = false }
        if (!s.aceptarTerminos) { err.aceptaTerminos = "Debes aceptar los términos"; valido = false }

        _usuarioState.value = s.copy(errores = err)
        return valido
    }

    // Validación Registro
    fun validarRegistro(): Boolean {
        val s = _usuarioState.value
        var valido = true
        val err = UsuarioErrores()

        if (s.nombre.isBlank()) { err.nombre = "Nombre requerido"; valido = false }
        if (s.email.isBlank()) { err.email = "Email requerido"; valido = false }
        if (s.password.length < 6) { err.password = "Mínimo 6 caracteres"; valido = false }
        if (s.password != s.confirmPassword) { err.confirmPassword = "No coinciden"; valido = false }
        if (!s.aceptarTerminos) { err.aceptaTerminos = "Requerido"; valido = false }

        _usuarioState.value = s.copy(errores = err)
        return valido
    }
}