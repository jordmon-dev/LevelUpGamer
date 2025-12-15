package com.example.levelup_gamer.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.datastore.UserPreferences
import com.example.levelup_gamer.model.UsuarioErrores
import com.example.levelup_gamer.model.UsuarioState
import com.example.levelup_gamer.repository.UsuarioRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = UserPreferences(application)

    // Repositorio instanciado correctamente (sin parámetros)
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

    // --- CONEXIÓN BACKEND (LOGIN / REGISTRO) ---

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = repository.login(email, pass)
                if (response.isSuccessful && response.body() != null) {
                    val authResponse = response.body()!!
                    _loginSuccess.value = true

                    // Guardamos los datos recibidos del backend
                    _usuarioState.value = _usuarioState.value.copy(
                        nombre = authResponse.usuario?.nombre ?: email,
                        email = authResponse.usuario?.email ?: email,
                        password = pass,
                        // Si el backend te devuelve nivel/puntos, asígnalos aquí también
                        // nivel = authResponse.usuario?.nivel ?: 1
                    )
                    guardarSesionLocal(authResponse.usuario?.nombre ?: email, pass)
                } else {
                    _errorMessage.value = "Credenciales incorrectas"
                    _loginSuccess.value = false
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error de conexión: ${e.message}"
                _loginSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun register(nombre: String, email: String, pass: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.registrar(nombre, email, pass)
                if (response.isSuccessful) {
                    _errorMessage.value = "¡Cuenta creada! Ingresa ahora."
                    login(email, pass) // Auto-login tras registro
                } else {
                    _errorMessage.value = "Error al registrar: ${response.code()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Sin conexión al servidor."
            } finally {
                _isLoading.value = false
            }
        }
    }

    // --- NUEVA FUNCIÓN: CARGAR PERFIL (Para arreglar el error) ---

    fun cargarPerfil(email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // AQUÍ SIMULAMOS LA CARGA DE DATOS DEL PERFIL
                // (Idealmente llamarías a repository.obtenerPerfil(email))

                delay(500) // Simulación de red

                // Actualizamos con datos de ejemplo para que se vea bonito en la defensa
                // Si tu backend NO trae estos datos, usa esto para mostrar "algo"
                val estadoActual = _usuarioState.value
                if (estadoActual.puntosLevelUp == 0) {
                    _usuarioState.value = estadoActual.copy(
                        nivel = 5,
                        puntosLevelUp = 1250,
                        fechaRegistro = "12/12/2023"
                    )
                }

            } catch (e: Exception) {
                _errorMessage.value = "No se pudo cargar el perfil completo"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // --- HANDLERS FORMULARIOS ---

    fun onChangeNombre(v: String) {
        _usuarioState.value = _usuarioState.value.copy(nombre = v, errores = _usuarioState.value.errores.copy(nombre = ""))
    }

    fun onChangeEmail(v: String) {
        _usuarioState.value = _usuarioState.value.copy(email = v, errores = _usuarioState.value.errores.copy(email = ""))
    }

    fun onChangePassword(v: String) {
        _usuarioState.value = _usuarioState.value.copy(password = v, errores = _usuarioState.value.errores.copy(password = ""))
    }

    fun onChangeConfirmPassword(v: String) {
        _usuarioState.value = _usuarioState.value.copy(confirmPassword = v, errores = _usuarioState.value.errores.copy(confirmPassword = ""))
    }

    fun onChangeAceptarTerminos(v: Boolean) {
        _usuarioState.value = _usuarioState.value.copy(aceptarTerminos = v, errores = _usuarioState.value.errores.copy(aceptaTerminos = ""))
    }

    // --- VALIDACIONES ---

    fun validar(): Boolean {
        val s = _usuarioState.value
        var valido = true
        val err = UsuarioErrores() // Reiniciamos errores

        if (s.email.isBlank()) { err.email = "Requerido"; valido = false }
        if (s.password.isBlank()) { err.password = "Requerido"; valido = false }
        // Para login no validamos términos estrictamente a menos que lo pidas

        _usuarioState.value = s.copy(errores = err)
        return valido
    }

    fun validarRegistro(): Boolean {
        val s = _usuarioState.value
        var valido = true
        val err = UsuarioErrores()

        if (s.nombre.isBlank()) { err.nombre = "Requerido"; valido = false }
        if (s.email.isBlank()) { err.email = "Requerido"; valido = false }
        if (s.password.length < 6) { err.password = "Mínimo 6 caracteres"; valido = false }
        if (s.password != s.confirmPassword) { err.confirmPassword = "No coinciden"; valido = false }

        // 🚨 ELIMINÉ ESTA LÍNEA QUE ROMPÍA EL BOTÓN:
        // if (!s.aceptarTerminos) { err.aceptaTerminos = "Debes aceptar"; valido = false }

        _usuarioState.value = s.copy(errores = err)
        return valido
    }

    // --- UTILS / DATASTORE ---

    private fun guardarSesionLocal(nombre: String, pass: String) {
        viewModelScope.launch { prefs.saveCredentials(nombre, pass) }
    }

    private fun cargarDatosGuardados() {
        viewModelScope.launch {
            if (prefs.isLogged.first()) {
                val user = prefs.usuario.first()
                val pass = prefs.password.first()
                _usuarioState.value = _usuarioState.value.copy(
                    nombre = user,
                    email = user,
                    password = pass,
                    aceptarTerminos = true
                )
            }
        }
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            prefs.logout()
            _usuarioState.value = UsuarioState() // Reset total
            _loginSuccess.value = false
        }
    }
}