package com.example.levelup_gamer.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.model.Favorito
import com.example.levelup_gamer.model.FavoritoDto
import com.example.levelup_gamer.remote.RetrofitInstance
import com.example.levelup_gamer.remote.UsuarioInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritoViewModel(application: Application) : AndroidViewModel(application) {

    private val _favoritos = MutableStateFlow<List<Favorito>>(emptyList())
    val favoritos: StateFlow<List<Favorito>> = _favoritos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Cargar la lista al iniciar (si hay usuario logueado)
    fun cargarFavoritos() {
        viewModelScope.launch {
            val usuario = UsuarioInstance.getCurrentUser()
            val email = usuario?.email

            if (!email.isNullOrEmpty()) {
                _isLoading.value = true
                try {
                    val response = RetrofitInstance.api.obtenerFavoritos(email)
                    if (response.isSuccessful && response.body() != null) {
                        _favoritos.value = response.body()!!
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    fun toggleFavorito(productoId: Long, nombreProducto: String) {
        viewModelScope.launch {
            val usuario = UsuarioInstance.getCurrentUser()
            val email = usuario?.email ?: return@launch

            val dto = FavoritoDto(email, productoId)

            try {
                val response = RetrofitInstance.api.toggleFavorito(dto)
                if (response.isSuccessful) {
                    Toast.makeText(getApplication(), "Lista de deseos actualizada", Toast.LENGTH_SHORT).show()
                    cargarFavoritos() // Recargamos la lista para ver el cambio
                }
            } catch (e: Exception) {
                Toast.makeText(getApplication(), "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función auxiliar para saber si un producto ya es favorito (útil para pintar el corazón rojo)
    fun esFavorito(productoId: Long): Boolean {
        return _favoritos.value.any { it.producto.id?.toLong() == productoId }
    }
}