package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelup_gamer.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

// 1. Clase de Modelo de Carrito (define un producto y su cantidad)
data class CarritoItem(
    val producto: Producto,
    val cantidad: Int
)

// 2. Definición del Estado de la UI para el Carrito
data class CarritoUiState(
    val items: List<CarritoItem> = listOf(
        // Datos de ejemplo iniciales (puedes borrarlos cuando uses Room)
        CarritoItem(
            producto = Producto("AC001", "Controlador Xbox Series X", 59990.0, "Accesorios", "Control inalámbrico"),
            cantidad = 1
        ),
        CarritoItem(
            producto = Producto("PP001", "Polera Gamer Personalizada", 14990.0, "Poleras", "Polera personalizable"),
            cantidad = 2
        )
    ),
    val subtotal: Double = 0.0,
    val descuento: Double = 0.0,
    val total: Double = 0.0
)

class CarritoViewModel : ViewModel() {

    // MutableStateFlow que mantiene el estado interno de los ítems en el carrito
    private val _items = MutableStateFlow(CarritoUiState().items)
    val items: StateFlow<List<CarritoItem>> = _items.asStateFlow()

    // 3. Flujo de Resumen de Compra (Lógica de negocio: Subtotal, Descuento, Total)
    val resumen: StateFlow<CarritoUiState> = _items.map { currentItems ->
        val subtotal = currentItems.sumOf { it.producto.precio * it.cantidad }

        // Regla de Negocio: Descuento del 10% si el subtotal supera los $50.000 CLP
        val descuento = if (subtotal > 50000) subtotal * 0.1 else 0.0

        val total = subtotal - descuento

        CarritoUiState(
            items = currentItems,
            subtotal = subtotal,
            descuento = descuento,
            total = total
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CarritoUiState()
    )


    // 4. Métodos para manipular el carrito

    /** Actualiza la cantidad de un ítem en el carrito. */
    fun onCantidadChange(item: CarritoItem, nuevaCantidad: Int) {
        if (nuevaCantidad > 0) {
            _items.update { currentItems ->
                currentItems.map {
                    if (it.producto.codigo == item.producto.codigo) {
                        it.copy(cantidad = nuevaCantidad)
                    } else it
                }
            }
        }
    }

    /** Elimina un ítem del carrito. */
    fun onEliminar(item: CarritoItem) {
        _items.update { currentItems ->
            currentItems.filter { it.producto.codigo != item.producto.codigo }
        }
    }

    /** Agrega un producto al carrito o incrementa la cantidad si ya existe. */
    fun onAgregarProducto(producto: Producto) {
        _items.update { currentItems ->
            val existingItem = currentItems.find { it.producto.codigo == producto.codigo }
            if (existingItem != null) {
                // Si ya existe, aumentar la cantidad
                currentItems.map {
                    if (it.producto.codigo == producto.codigo) {
                        it.copy(cantidad = it.cantidad + 1)
                    } else it
                }
            } else {
                // Si no existe, añadir uno nuevo
                currentItems + CarritoItem(producto = producto, cantidad = 1)
            }
        }
    }
}