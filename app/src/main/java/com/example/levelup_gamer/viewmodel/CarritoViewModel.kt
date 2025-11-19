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
    val total: Double = 0.0,
    val porcentajeDescuento: Double = 0.0
)

class CarritoViewModel : ViewModel() {

    // StateFlow para el resumen del carrito
    private val _resumen = MutableStateFlow(ResumenCarrito())
    val resumen: StateFlow<ResumenCarrito> = _resumen

    // Propiedad para acceder a los items del carrito
    private val itemsCarrito: List<CarritoItem>
        get() = _resumen.value.items

    // Función para agregar producto al carrito
    fun agregarProducto(producto: Producto, correoUsuario: String = "") {
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

            // Calcular nuevos totales con el descuento correcto
            val porcentajeDescuento = obtenerPorcentajeDescuento(correoUsuario)
            val nuevoSubtotal = calcularSubtotal(itemsActualizados)
            val nuevoDescuento = nuevoSubtotal * porcentajeDescuento
            val nuevoTotal = nuevoSubtotal - nuevoDescuento

            resumenActual.copy(
                items = itemsActualizados,
                subtotal = nuevoSubtotal,
                descuento = nuevoDescuento,
                total = nuevoTotal,
                porcentajeDescuento = porcentajeDescuento
            )
        }
    }

    // Función para cambiar cantidad de un producto
    fun onCantidadChange(item: CarritoItem, nuevaCantidad: Int, correoUsuario: String = "") {
        _resumen.update { resumenActual ->
            val itemsActualizados = resumenActual.items.toMutableList()
            val itemIndex = itemsActualizados.indexOfFirst { it.producto.codigo == item.producto.codigo }

            if (itemIndex != -1) {
                if (nuevaCantidad > 0) {
                    itemsActualizados[itemIndex] = item.copy(cantidad = nuevaCantidad)
                } else {
                    itemsActualizados.removeAt(itemIndex)
                }

                // Recalcular totales con el descuento correcto
                val porcentajeDescuento = obtenerPorcentajeDescuento(correoUsuario)
                val nuevoSubtotal = calcularSubtotal(itemsActualizados)
                val nuevoDescuento = nuevoSubtotal * porcentajeDescuento
                val nuevoTotal = nuevoSubtotal - nuevoDescuento

                resumenActual.copy(
                    items = itemsActualizados,
                    subtotal = nuevoSubtotal,
                    descuento = nuevoDescuento,
                    total = nuevoTotal,
                    porcentajeDescuento = porcentajeDescuento
                )
            } else {
                resumenActual
            }
        }
    }

    // Función para eliminar producto del carrito
    fun onEliminar(item: CarritoItem, correoUsuario: String = "") {
        _resumen.update { resumenActual ->
            val itemsActualizados = resumenActual.items.toMutableList()
            itemsActualizados.removeAll { it.producto.codigo == item.producto.codigo }

            // Recalcular totales con el descuento correcto
            val porcentajeDescuento = obtenerPorcentajeDescuento(correoUsuario)
            val nuevoSubtotal = calcularSubtotal(itemsActualizados)
            val nuevoDescuento = nuevoSubtotal * porcentajeDescuento
            val nuevoTotal = nuevoSubtotal - nuevoDescuento

            resumenActual.copy(
                items = itemsActualizados,
                subtotal = nuevoSubtotal,
                descuento = nuevoDescuento,
                total = nuevoTotal,
                porcentajeDescuento = porcentajeDescuento
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

    // Función para obtener el porcentaje de descuento basado en el correo
    private fun obtenerPorcentajeDescuento(correoUsuario: String): Double {
        return if (correoUsuario.endsWith("@duocuc.cl")) {
            0.20 // 20% de descuento para estudiantes Duoc
        } else {
            0.10 // 10% de descuento para usuarios regulares
        }
    }

    // Función para actualizar resumen con el correo del usuario
    fun actualizarResumen(correoUsuario: String) {
        _resumen.update { resumenActual ->
            val porcentajeDescuento = obtenerPorcentajeDescuento(correoUsuario)
            val nuevoSubtotal = calcularSubtotal(resumenActual.items)
            val nuevoDescuento = nuevoSubtotal * porcentajeDescuento
            val nuevoTotal = nuevoSubtotal - nuevoDescuento

            resumenActual.copy(
                subtotal = nuevoSubtotal,
                descuento = nuevoDescuento,
                total = nuevoTotal,
                porcentajeDescuento = porcentajeDescuento
            )
        }
    }

    // Función para obtener el texto del descuento
    fun obtenerTextoDescuento(porcentajeDescuento: Double): String {
        return when (porcentajeDescuento) {
            0.20 -> "Descto 20%:"
            0.10 -> "Descto 10%:"
            else -> "Descuento:"
        }
    }
}