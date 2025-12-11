// CarritoItem.kt (SOLO ESTE CÓDIGO, reemplaza todo)
package com.example.levelup_gamer.modelo

// Modelo para la API de Spring Boot
data class CarritoItemModel(
    val id: Int? = null,
    val productoId: Int,
    val productoNombre: String,
    val productoPrecio: Double,
    val productoDescripcion: String? = null,
    val productoImagen: String? = null,
    val productoStock: Int,
    val cantidad: Int,
    val usuarioEmail: String,
    val fechaAgregado: String? = null
)


// Modelo para la UI (lo que usa tu pantalla)
data class CarritoItemUI(
    val id: Int? = null,
    val productoId: Int,
    val nombre: String,  // <-- IMPORTANTE: usa "nombre" no "productoNombre"
    val precio: Double,  // <-- IMPORTANTE: usa "precio" no "productoPrecio"
    val descripcion: String? = null,
    val imagen: String? = null,
    val stock: Int,
    val cantidad: Int,
    val usuarioEmail: String,
    val fechaAgregado: String? = null
)

data class CarritoResumenUI(
    val items: List<CarritoItemUI>,
    val subtotal: Double,
    val descuento: Double,
    val total: Double,
    val cantidadTotal: Int
)

// Request para la API
data class CarritoRequest(
    val productoId: Int,
    val cantidad: Int,
    val usuarioEmail: String
)

// Funciones para convertir
fun CarritoItemModel.toCarritoItemUI(): CarritoItemUI {
    return CarritoItemUI(
        id = this.id,
        productoId = this.productoId,
        nombre = this.productoNombre,  // Convierte productoNombre → nombre
        precio = this.productoPrecio,  // Convierte productoPrecio → precio
        descripcion = this.productoDescripcion,
        imagen = this.productoImagen,
        stock = this.productoStock,
        cantidad = this.cantidad,
        usuarioEmail = this.usuarioEmail,
        fechaAgregado = this.fechaAgregado
    )
}

fun CarritoResumenModel.toCarritoResumenUI(): CarritoResumenUI {
    return CarritoResumenUI(
        items = this.items.map { it.toCarritoItemUI() },
        subtotal = this.subtotal,
        descuento = this.descuento,
        total = this.total,
        cantidadTotal = this.cantidadTotal
    )
}