package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.modelo.CarritoItemUI
import com.example.levelup_gamer.modelo.CarritoResumenUI
import com.example.levelup_gamer.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    // Propiedad de conveniencia para acceder al resumen
    val resumen: CarritoResumenUI
        get() = _uiState.value.resumen

    fun actualizarResumen(email: String) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            try {
                // Usar API real
                val carrito = repository.getCarritoUsuario(email)

                carrito?.let {
                    _uiState.update { state ->
                        state.copy(
                            resumen = it,
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = "Error al cargar carrito: ${e.message}"
                    )
                }
            }
        }
    }

    fun onCantidadChange(item: CarritoItemUI, nuevaCantidad: Int, email: String) {
        viewModelScope.launch {
            try {
                item.id?.let { itemId ->
                    val exitoso = repository.actualizarCantidad(itemId, nuevaCantidad)

                    if (exitoso) {
                        actualizarResumen(email)
                    }
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        errorMessage = "Error al actualizar cantidad: ${e.message}"
                    )
                }
            }
        }
    }

    fun onEliminar(item: CarritoItemUI, email: String) {
        viewModelScope.launch {
            try {
                item.id?.let { itemId ->
                    val exitoso = repository.eliminarDelCarrito(itemId)

                    if (exitoso) {
                        actualizarResumen(email)
                    }
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        errorMessage = "Error al eliminar item: ${e.message}"
                    )
                }
            }
        }
    }
}