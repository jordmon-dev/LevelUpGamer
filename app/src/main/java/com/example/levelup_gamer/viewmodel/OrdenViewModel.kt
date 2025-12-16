package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.model.Orden
import com.example.levelup_gamer.remote.RetrofitInstance
import com.example.levelup_gamer.remote.UsuarioInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrdenViewModel : ViewModel() {

    private val _ordenes = MutableStateFlow<List<Orden>>(emptyList())
    val ordenes: StateFlow<List<Orden>> = _ordenes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun cargarOrdenes() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                // 1. Obtenemos el token del usuario logueado (Admin)
                val token = UsuarioInstance.getBearerToken()

                if (token != null) {
                    // 2. Llamamos a la API enviando el token
                    val response = RetrofitInstance.api.obtenerOrdenes(token)

                    if (response.isSuccessful) {
                        // ¡Éxito! Guardamos la lista, invirtiendo el orden para ver las nuevas primero
                        _ordenes.value = response.body()?.reversed() ?: emptyList()
                    } else {
                        _error.value = "Error ${response.code()}: No tienes permiso o falló el servidor."
                    }
                } else {
                    _error.value = "No hay sesión de administrador activa."
                }
            } catch (e: Exception) {
                _error.value = "Fallo de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}