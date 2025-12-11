package com.example.levelup_gamer.modelo

// Modelo para las respuestas de la API (lo que viene del backend)
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

data class CarritoResumenModel(
    val items: List<CarritoItemModel>,
    val subtotal: Double,
    val descuento: Double,
    val total: Double,
    val cantidadTotal: Int
)

// Modelo para la UI (lo que muestra tu pantalla)
data class CarritoItemUI(
    val id: Int? = null,
    val productoId: Int,
    val productoNombre: String,
    val productoPrecio: Double,
    val productoDescripcion: String? = null,
    val productoImagen: String? = null,
    val productoStock: Int,
    val producto: Producto? = null,
    val cantidad: Int,
    val usuarioEmail: String,
    val fechaAgregado: String? = null
) {
    val nombre: String get() = productoNombre
    val precio: Double get() = productoPrecio

    constructor(
        id: Int? = null,
        producto: Producto,
        cantidad: Int,
        usuarioEmail: String
    ) : this(
        id = id,
        productoId = producto.id,
        productoNombre = producto.nombre,
        productoPrecio = producto.precio,
        productoDescripcion = producto.descripcion,
        productoImagen = producto.imagen,
        productoStock = producto.stock,
        producto = producto,
        cantidad = cantidad,
        usuarioEmail = usuarioEmail
    )
}

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

// Funciones de conversión
fun CarritoItemModel.toCarritoItemUI(): CarritoItemUI {
    return CarritoItemUI(
        id = this.id,
        productoId = this.productoId,
        productoNombre = this.productoNombre,
        productoPrecio = this.productoPrecio,
        productoDescripcion = this.productoDescripcion,
        productoImagen = this.productoImagen,
        productoStock = this.productoStock,
        producto = null,
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