package com.example.levelup_gamer.viewmodel

import androidx.lifecycle.ViewModel
import com.example.levelup_gamer.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

// Data classes necesarias
data class CarritoItem(
    val producto: Producto,
    val cantidad: Int = 1
)

data class ResumenCarrito(
    val items: List<CarritoItem> = emptyList(),
    val subtotal: Double = 0.0,
    val descuento: Double = 0.0,
    val total: Double = 0.0
)

class CarritoViewModel : ViewModel() {

    // StateFlow para el resumen del carrito
    private val _resumen = MutableStateFlow(ResumenCarrito())
    val resumen: StateFlow<ResumenCarrito> = _resumen

    // Función para agregar producto al carrito
    fun agregarProducto(producto: Producto) {
        _resumen.update { resumenActual ->
            val itemsActualizados = resumenActual.items.toMutableList()

            // Verificar si el producto ya está en el carrito
            val itemExistente = itemsActualizados.find { it.producto.codigo == producto.codigo }

            if (itemExistente != null) {
                // Si ya existe, aumentar la cantidad
                val nuevoItem = itemExistente.copy(cantidad = itemExistente.cantidad + 1)
                itemsActualizados[itemsActualizados.indexOf(itemExistente)] = nuevoItem
            } else {
                // Si no existe, agregar nuevo item
                itemsActualizados.add(CarritoItem(producto = producto, cantidad = 1))
            }

            // Calcular nuevos totales
            val nuevoSubtotal = calcularSubtotal(itemsActualizados)
            val nuevoDescuento = calcularDescuento(nuevoSubtotal)
            val nuevoTotal = nuevoSubtotal - nuevoDescuento

            resumenActual.copy(
                items = itemsActualizados,
                subtotal = nuevoSubtotal,
                descuento = nuevoDescuento,
                total = nuevoTotal
            )
        }
    }

    // Función para cambiar cantidad de un producto
    fun onCantidadChange(item: CarritoItem, nuevaCantidad: Int) {
        _resumen.update { resumenActual ->
            val itemsActualizados = resumenActual.items.toMutableList()
            val itemIndex = itemsActualizados.indexOfFirst { it.producto.codigo == item.producto.codigo }

            if (itemIndex != -1) {
                if (nuevaCantidad > 0) {
                    itemsActualizados[itemIndex] = item.copy(cantidad = nuevaCantidad)
                } else {
                    itemsActualizados.removeAt(itemIndex)
                }

                // Recalcular totales
                val nuevoSubtotal = calcularSubtotal(itemsActualizados)
                val nuevoDescuento = calcularDescuento(nuevoSubtotal)
                val nuevoTotal = nuevoSubtotal - nuevoDescuento

                resumenActual.copy(
                    items = itemsActualizados,
                    subtotal = nuevoSubtotal,
                    descuento = nuevoDescuento,
                    total = nuevoTotal
                )
            } else {
                resumenActual
            }
        }
    }

    // Función para eliminar producto del carrito
    fun onEliminar(item: CarritoItem) {
        _resumen.update { resumenActual ->
            val itemsActualizados = resumenActual.items.toMutableList()
            itemsActualizados.removeAll { it.producto.codigo == item.producto.codigo }

            // Recalcular totales
            val nuevoSubtotal = calcularSubtotal(itemsActualizados)
            val nuevoDescuento = calcularDescuento(nuevoSubtotal)
            val nuevoTotal = nuevoSubtotal - nuevoDescuento

            resumenActual.copy(
                items = itemsActualizados,
                subtotal = nuevoSubtotal,
                descuento = nuevoDescuento,
                total = nuevoTotal
            )
        }
    }

    // Función para limpiar el carrito
    fun limpiarCarrito() {
        _resumen.value = ResumenCarrito()
    }

    // Función para calcular subtotal
    private fun calcularSubtotal(items: List<CarritoItem>): Double {
        return items.sumOf { it.producto.precio * it.cantidad }
    }

    // Función para calcular descuento (10% de descuento)
    private fun calcularDescuento(subtotal: Double): Double {
        return subtotal * 0.10 // 10% de descuento
    }

    // Función para actualizar resumen (por si la necesitas)
    private fun actualizarResumen() {
        _resumen.update { resumenActual ->
            val nuevoSubtotal = calcularSubtotal(resumenActual.items)
            val nuevoDescuento = calcularDescuento(nuevoSubtotal)
            val nuevoTotal = nuevoSubtotal - nuevoDescuento

            resumenActual.copy(
                subtotal = nuevoSubtotal,
                descuento = nuevoDescuento,
                total = nuevoTotal
            )
        }
    }
}