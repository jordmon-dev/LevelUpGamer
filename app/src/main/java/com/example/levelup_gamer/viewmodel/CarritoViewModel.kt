package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.model.CarritoItem
import com.example.levelup_gamer.model.Orden
import com.example.levelup_gamer.repository.CarritoRepository
// ✅ CORREGIDO: Importamos tu objeto real "RetrofitInstance"
import com.example.levelup_gamer.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CarritoUiState(
    val items: List<CarritoItem> = emptyList(),
    val total: Double = 0.0,
    val isLoading: Boolean = false
)

class CarritoViewModel : ViewModel() {

    private val repository = CarritoRepository()

    private val _uiState = MutableStateFlow(CarritoUiState())
    val uiState: StateFlow<CarritoUiState> = _uiState.asStateFlow()

    init {
        cargarCarrito()
    }

    fun cargarCarrito() {
        viewModelScope.launch {
            val items = repository.obtenerCarrito()

            // Calculamos el total
            val total = items.sumOf { it.producto.precio.toDouble() * it.cantidad }

            _uiState.value = _uiState.value.copy(
                items = items,
                total = total
            )
        }
    }

    fun agregarProducto(id: Int, nombre: String, precio: Int, imagen: String) {
        viewModelScope.launch {
            repository.agregarAlCarrito(id, nombre, precio, imagen)
            cargarCarrito() // Recargamos para actualizar la UI
        }
    }

    fun eliminarProducto(id: Int) {
        viewModelScope.launch {
            repository.eliminarDelCarrito(id)
            cargarCarrito()
        }
    }

    fun vaciarCarrito() {
        viewModelScope.launch {
            repository.vaciarCarrito()
            cargarCarrito()
        }
    }

    // ✅ FUNCIÓN CORREGIDA: Usa RetrofitInstance.api
    fun realizarCompra(nombreUsuario: String, emailUsuario: String, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                val totalActual = _uiState.value.total.toInt()
                if (totalActual == 0) return@launch

                // 1. Preparamos el objeto Orden
                val nuevaOrden = Orden(
                    numeroOrden = System.currentTimeMillis(),
                    total = totalActual,
                    nombreCliente = nombreUsuario,
                    emailCliente = emailUsuario
                )

                // 2. Llamamos al Backend usando TU instancia correcta
                // Antes: RetrofitClient.apiService.crearOrden(...) -> ERROR
                // Ahora: RetrofitInstance.api.crearOrden(...) -> CORRECTO
                val response = RetrofitInstance.api.crearOrden(nuevaOrden)

                if (response.isSuccessful) {
                    vaciarCarrito()
                    onSuccess()
                } else {
                    onError()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onError()
            }
        }
    }
}