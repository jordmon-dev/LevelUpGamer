package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.model.CarritoItem
import com.example.levelup_gamer.repository.CarritoRepository
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
            // Nota: Nos aseguramos de convertir el precio a Double por si viene como Int desde Producto
            val total = items.sumOf { it.producto.precio.toDouble() * it.cantidad }

            _uiState.value = _uiState.value.copy(
                items = items,
                total = total
            )
        }
    }

    // ✅ CORRECCIÓN: 'imagen' ahora es String para aceptar URLs
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
}