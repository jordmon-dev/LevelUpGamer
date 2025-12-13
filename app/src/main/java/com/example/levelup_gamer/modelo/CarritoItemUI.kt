package com.example.levelup_gamer.modelo

// Modelo para la API de Spring Boot
data class CarritoItemModel(
    val id: Int? = null,
    val productoId: Int,
    val productoNombre: String,
    val productoPrecio: Double,
    val productoDescripcion: String? = null,
    val productoImagen: Int? = null,
    val productoStock: Int,
    val cantidad: Int,
    val usuarioEmail: String,
    val fechaAgregado: String? = null
)

// Modelo para la UI (lo que usa tu pantalla)
data class CarritoItemUI(
    val id: Int? = null,
    val cantidad: Int,
    val producto: Producto,
    val usuarioEmail: String,
    val fechaAgregado: String? = null
)

data class CarritoResumen(
    val items: List<CarritoItemModel>,
    val subtotal: Double,
    val descuento: Double,
    val total: Double,
    val cantidadTotal: Int
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
        cantidad = this.cantidad,
        producto = Producto(
            id = this.productoId,
            codigo = "", // Se necesita pero no viene del modelo API
            nombre = this.productoNombre,
            descripcion = this.productoDescripcion ?: "",
            precio = this.productoPrecio,
            stock = this.productoStock,
            imagen = this.productoImagen ?: 0,
            categoria = "" // Se necesita pero no viene del modelo API
        ),
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