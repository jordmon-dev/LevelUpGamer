// CarritoViewModel.kt (usa el que ya tienes, pero actualiza estas funciones)
package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.modelo.CarritoItemUI
import com.example.levelup_gamer.modelo.CarritoRequest
import com.example.levelup_gamer.modelo.CarritoResumenUI
import com.example.levelup_gamer.modelo.toCarritoResumenUI
import com.example.levelup_gamer.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Si ya tienes CarritoUiState, mantenlo igual
data class CarritoUiState(
    val resumen: CarritoResumenUI = CarritoResumenUI(
        items = emptyList(),
        subtotal = 0.0,
        descuento = 0.0,
        total = 0.0,
        cantidadTotal = 0
    ),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class CarritoViewModel : ViewModel() {
    private val repository = CarritoRepository()

    private val _uiState = MutableStateFlow(CarritoUiState())
    val uiState: StateFlow<CarritoUiState> = _uiState.asStateFlow()

    // ACTUALIZA ESTA FUNCIÓN
    fun actualizarResumen(email: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val result = repository.obtenerCarrito(email)

            result.onSuccess { carritoModel ->
                val carritoUI = carritoModel.toCarritoResumenUI()
                _uiState.value = _uiState.value.copy(
                    resumen = carritoUI,
                    isLoading = false
                )
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error: ${error.message}"
                )
            }
        }
    }

    // ACTUALIZA ESTA FUNCIÓN
    fun onCantidadChange(item: CarritoItemUI, nuevaCantidad: Int, email: String) {
        viewModelScope.launch {
            item.id?.let { id ->
                val result = repository.actualizarCantidad(id, nuevaCantidad, email)

                result.onSuccess {
                    actualizarResumen(email)
                }.onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Error al actualizar: ${error.message}"
                    )
                }
            }
        }
    }

    // ACTUALIZA ESTA FUNCIÓN
    fun onEliminar(item: CarritoItemUI, email: String) {
        viewModelScope.launch {
            item.id?.let { id ->
                val result = repository.eliminarDelCarrito(id, email)

                result.onSuccess {
                    actualizarResumen(email)
                }.onFailure { error ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Error al eliminar: ${error.message}"
                    )
                }
            }
        }
    }

    fun limpiarCarrito(email: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                // Obtén todos los items actuales
                val currentItems = _uiState.value.resumen.items

                // Elimina cada item
                currentItems.forEach { item ->
                    item?.id?.let { id ->
                        repository.eliminarDelCarrito(id, email)
                    }
                }

                // Actualiza el resumen (ahora estará vacío)
                actualizarResumen(email)

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Error al limpiar el carrito: ${e.message}",
                    isLoading = false
                )
            }
        }
    }

    // Nueva función para agregar productos
    fun agregarProducto(productoId: Int, cantidad: Int, email: String) {
        viewModelScope.launch {
            val request = CarritoRequest(productoId, cantidad, email)
            val result = repository.agregarAlCarrito(request)

            result.onSuccess {
                actualizarResumen(email)
            }.onFailure { error ->
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Error al agregar: ${error.message}"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}