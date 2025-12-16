package com.example.levelup_gamer.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.datastore.UserPreferences
import com.example.levelup_gamer.model.LoginDto
import com.example.levelup_gamer.model.RegistroDto
import com.example.levelup_gamer.model.Usuario
import com.example.levelup_gamer.model.UsuarioErrores
import com.example.levelup_gamer.model.UsuarioState
import com.example.levelup_gamer.remote.RetrofitInstance
import com.example.levelup_gamer.remote.UsuarioInstance
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UsuarioViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = UserPreferences(application)

    // --- Estados de la UI ---
    private val _usuarioState = MutableStateFlow(UsuarioState())
    val usuarioState: StateFlow<UsuarioState> = _usuarioState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    private val _fotoPerfil = MutableStateFlow<String?>(null)
    val fotoPerfil: StateFlow<String?> = _fotoPerfil.asStateFlow()

    init {
        cargarDatosGuardados()
    }

    // Funcion para actualizar la foto de perfil!!
    fun actualizarFotoPerfil(uri: String) {
        _fotoPerfil.value = uri
        viewModelScope.launch {
            prefs.saveProfilePhoto(uri)
        }
    }

    // --- CONEXIÓN BACKEND (LOGIN CORREGIDO) ---

    // ✅ CAMBIO IMPORTANTE: Agregamos 'context' y 'onSuccess' para guardar el token
    fun login(email: String, pass: String, context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                // 1. Llamada a la API
                val loginDto = LoginDto(email = email, password = pass)
                val response = RetrofitInstance.api.login(loginDto)

                if (response.isSuccessful && response.body() != null) {
                    // 2. Recibimos el objeto AuthResponse (NO es un mapa)
                    val authResponse = response.body()!!

                    // ✅ CORRECCIÓN: Usamos punto (.) en lugar de ["token"]
                    val token = authResponse.token ?: ""
                    val usuarioObj = authResponse.usuario // ¡Ya viene listo!

                    if (token.isNotEmpty() && usuarioObj != null) {
                        // 3. Guardamos en memoria y disco
                        UsuarioInstance.saveUser(context, usuarioObj, token)

                        _loginSuccess.value = true

                        // Actualizamos estado visual
                        _usuarioState.value = _usuarioState.value.copy(
                            nombre = usuarioObj.nombre ?: email,
                            email = usuarioObj.email ?: email,
                            password = pass
                            // rol = usuarioObj.rol (Si lo tienes agregado)
                        )

                        onSuccess()
                    } else {
                        _errorMessage.value = "Error: Respuesta vacía del servidor."
                    }
                } else {
                    _errorMessage.value = "Credenciales incorrectas"
                    _loginSuccess.value = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "Error de conexión: ${e.message}"
                _loginSuccess.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Mantenemos el registro igual, pero usando RetrofitInstance para consistencia
    fun register(nombre: String, email: String, pass: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val registroDto = RegistroDto(
                    nombre = nombre,
                    apellidos = "Usuario",
                    email = email,
                    password = pass,
                    direccion = "Sin dirección",
                    region = "RM",
                    comuna = "Santiago"
                )
                val response = RetrofitInstance.api.registrar(registroDto)

                if (response.isSuccessful) {
                    _errorMessage.value = "¡Cuenta creada! Ingresa ahora."
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

    // --- CARGAR PERFIL ---
    fun cargarPerfil(email: String) {
        viewModelScope.launch {
            // Intenta cargar desde UsuarioInstance primero (más rápido)
            val user = UsuarioInstance.getCurrentUser()
            if (user != null && user.email == email) {
                val estadoActual = _usuarioState.value
                if (estadoActual.puntosLevelUp == 0) {
                    _usuarioState.value = estadoActual.copy(
                        nombre = user.nombre ?: "",
                        nivel = 5,
                        puntosLevelUp = 1250
                    )
                }
            }
        }
    }

    // --- HANDLERS Y VALIDACIONES (INTACTOS) ---
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

    fun validar(): Boolean {
        val s = _usuarioState.value
        var valido = true
        val err = UsuarioErrores()
        if (s.email.isBlank()) { err.email = "Requerido"; valido = false }
        if (s.password.isBlank()) { err.password = "Requerido"; valido = false }
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
            // Limpiamos también UsuarioInstance
            // Necesitamos contexto para logout completo, pero limpiar memoria ayuda
            // UsuarioInstance.logout(context) <- Ideal, pero aquí solo limpiamos state
            _usuarioState.value = UsuarioState()
            _loginSuccess.value = false
        }
    }
}