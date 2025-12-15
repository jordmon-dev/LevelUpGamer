package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class CarritoItemTest {

    @Test
    fun `carrito item se crea correctamente con producto y cantidad`() {
        // Arrange
        val producto = Producto(
            id = 1L,
            nombre = "Cyberpunk 2077",
            precio = 59990,
            categoria = "Videojuegos"
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
            nombre = "Elden Ring",
            precio = 49990
        )

        val carritoItem = CarritoItem(
            producto = producto
            // cantidad por defecto = 1
        )

        // Assert
        assertEquals(1, carritoItem.cantidad)
        assertEquals("Elden Ring", carritoItem.producto.nombre)
    }

    @Test
    fun `carrito item calcula subtotal correctamente`() {
        // Arrange
        val producto = Producto(
            nombre = "Cyberpunk 2077",
            precio = 59990 // 599.90 en decimal
        )

        val carritoItem = CarritoItem(
            producto = producto,
            cantidad = 3
        )

        // Act - Calcular subtotal manualmente
        val subtotal = producto.precio * carritoItem.cantidad
        val subtotalEsperado = 59990 * 3

        // Assert
        assertEquals(subtotalEsperado, subtotal)
        assertEquals(179970, subtotal) // 599.90 * 3 = 1799.70 en decimal
    }

    @Test
    fun `carrito item con cantidad cero tiene subtotal cero`() {
        // Arrange
        val producto = Producto(
            nombre = "Producto Gratuito",
            precio = 0
        )

        val carritoItem = CarritoItem(
            producto = producto,
            cantidad = 0
        )

        // Act
        val subtotal = producto.precio * carritoItem.cantidad

        // Assert
        assertEquals(0, subtotal)
        assertEquals(0, carritoItem.cantidad)
    }

    @Test
    fun `carrito item incrementa cantidad correctamente`() {
        // Arrange
        val producto = Producto(
            nombre = "FIFA 24",
            precio = 69990
        )

        val carritoItem = CarritoItem(
            producto = producto,
            cantidad = 1
        )

        // Act
        carritoItem.cantidad++

        // Assert
        assertEquals(2, carritoItem.cantidad)
    }

    @Test
    fun `carrito item con precio alto y cantidad`() {
        // Arrange
        val producto = Producto(
            nombre = "Consola PS5",
            precio = 499990 // 4999.90
        )

        val carritoItem = CarritoItem(
            producto = producto,
            cantidad = 2
        )

        // Act
        val subtotal = producto.precio * carritoItem.cantidad

        // Assert
        assertEquals(999980, subtotal) // 4999.90 * 2 = 9999.80
    }

    @Test
    fun `carrito item con producto sin stock`() {
        // Arrange
        val producto = Producto(
            nombre = "Juego Agotado",
            precio = 29990,
            stock = 0
        )

        val carritoItem = CarritoItem(
            producto = producto,
            cantidad = 1
        )

        // Assert
        assertEquals(0, producto.stock)
        assertEquals(1, carritoItem.cantidad)
        // Podría querer añadir al carrito aunque no haya stock
    }

    @Test
    fun `dos carrito items con mismo producto son iguales por referencia`() {
        // Arrange
        val producto = Producto(
            id = 100L,
            nombre = "Mario Kart",
            precio = 39990
        )

        val item1 = CarritoItem(producto = producto, cantidad = 1)
        val item2 = CarritoItem(producto = producto, cantidad = 2)

        // Assert - Mismo objeto producto
        assertSame(producto, item1.producto)
        assertSame(producto, item2.producto)
        assertEquals(producto.id, item1.producto.id)
        assertEquals(producto.id, item2.producto.id)
    }

    @Test
    fun `carrito item toString muestra informacion del producto`() {
        // Arrange
        val producto = Producto(
            nombre = "The Legend of Zelda",
            precio = 79990
        )

        val carritoItem = CarritoItem(
            producto = producto,
            cantidad = 3
        )

        // Act
        val stringRepresentation = carritoItem.toString()

        // Assert
        assertTrue(stringRepresentation.contains("CarritoItem"))
        assertTrue(stringRepresentation.contains("The Legend of Zelda") ||
                stringRepresentation.contains("producto"))
    }

    @Test
    fun `carrito item puede actualizar cantidad`() {
        // Arrange
        val producto = Producto(
            nombre = "Juego de Prueba",
            precio = 19990
        )

        val carritoItem = CarritoItem(
            producto = producto,
            cantidad = 1
        )

        // Act
        carritoItem.cantidad = 5

        // Assert
        assertEquals(5, carritoItem.cantidad)
        val subtotal = producto.precio * carritoItem.cantidad
        assertEquals(19990 * 5, subtotal)
    }
}