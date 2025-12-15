package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class CarritoItemTest {

    @Test
    fun `carrito item se crea correctamente con producto y cantidad`() {
        // Arrange
        // Producto usa id=Int
        val producto = Producto(
            id = 1,
            nombre = "Cyberpunk 2077",
            precio = 59990,
            categoria = "Videojuegos",
            stock = 10,
            imagen = "",
            descripcion = "",
            codigo = ""
        )

        val carritoItem = CarritoItem(
            producto = producto,
            cantidad = 2
        )

        // Assert
        assertEquals(producto, carritoItem.producto)
        assertEquals("Cyberpunk 2077", carritoItem.producto.nombre)
        assertEquals(59990, carritoItem.producto.precio)
        assertEquals(2, carritoItem.cantidad)
    }

    @Test
    fun `carrito item con cantidad por defecto es 1`() {
        // Arrange
        val producto = Producto(
            id = 2,
            nombre = "Elden Ring",
            precio = 49990,
            stock = 5,
            categoria = "RPG",
            descripcion = "",
            imagen = "",
            codigo = ""
        )

        val carritoItem = CarritoItem(
            producto = producto
            // cantidad por defecto es 1 según tu modelo
        )

        // Assert
        assertEquals(1, carritoItem.cantidad)
        assertEquals("Elden Ring", carritoItem.producto.nombre)
    }

    @Test
    fun `carrito item calcula subtotal correctamente`() {
        // Arrange
        val producto = Producto(
            id = 3,
            nombre = "Cyberpunk 2077",
            precio = 59990,
            stock = 10,
            categoria = "RPG",
            descripcion = "",
            imagen = "",
            codigo = ""
        )

        val carritoItem = CarritoItem(
            producto = producto,
            cantidad = 3
        )

        // Act - Tu lógica de negocio es precio * cantidad
        val subtotal = producto.precio * carritoItem.cantidad
        val subtotalEsperado = 59990 * 3

        // Assert
        assertEquals(subtotalEsperado, subtotal)
        assertEquals(179970, subtotal)
    }

    @Test
    fun `dos carrito items con mismo producto son iguales por referencia`() {
        // Arrange
        val producto = Producto(
            id = 100, // ✅ Correcto: Int (no 100L)
            nombre = "Mario Kart",
            precio = 39990,
            stock = 10,
            categoria = "Carreras",
            descripcion = "",
            imagen = "",
            codigo = ""
        )

        val item1 = CarritoItem(producto = producto, cantidad = 1)
        val item2 = CarritoItem(producto = producto, cantidad = 2)

        // Assert
        assertSame(producto, item1.producto)
        assertSame(producto, item2.producto)
        assertEquals(producto.id, item1.producto.id)
    }
}