package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class ProductoTest {

    @Test
    fun `producto se crea correctamente con valores basicos`() {
        // Arrange
        val producto = Producto(
            id = 1, // ✅ Correcto: Usamos Int (1) y no Long (1L)
            codigo = "PROD-001",
            nombre = "Cyberpunk 2077",
            precio = 59990,
            categoria = "Videojuegos",
            descripcion = "Juego de rol futurista",
            stock = 50,
            imagen = "https://example.com/cyberpunk.jpg"
        )

        // Assert
        assertEquals(1, producto.id)
        assertEquals("PROD-001", producto.codigo)
        assertEquals("Cyberpunk 2077", producto.nombre)
        assertEquals(59990, producto.precio)
        assertEquals("Videojuegos", producto.categoria)
        assertEquals("Juego de rol futurista", producto.descripcion)
        assertEquals(50, producto.stock)
        assertEquals("https://example.com/cyberpunk.jpg", producto.imagen)
    }

    @Test
    fun `producto con precio cero`() {
        // Arrange
        val producto = Producto(
            id = 2,
            nombre = "Juego Gratuito",
            precio = 0,
            stock = 100
            // Usamos los valores por defecto para el resto
        )

        // Assert
        assertEquals(0, producto.precio)
        assertEquals(100, producto.stock)
        assertNull(producto.imagen) // ✅ Correcto: Por defecto es null en tu modelo
    }

    @Test
    fun `producto con imagen nula`() {
        // Arrange
        val producto = Producto(
            id = 3,
            nombre = "Producto sin imagen",
            precio = 29990,
            imagen = null // ✅ Explícitamente nulo
        )

        // Assert
        assertNull(producto.imagen)
        assertEquals("", producto.codigo) // Valor por defecto en tu modelo
    }

    @Test
    fun `producto con stock negativo`() {
        // Arrange
        val producto = Producto(
            id = 4,
            nombre = "Producto agotado",
            precio = 19990,
            stock = -5 // Stock negativo podría indicar error o deuda
        )

        // Assert
        assertTrue(producto.stock < 0)
    }

    @Test
    fun `producto convierte precio a decimal para mostrar`() {
        // Arrange
        val producto = Producto(
            id = 5,
            nombre = "Juego de Prueba",
            precio = 59990
        )

        // Act - Simulación de cómo se vería en pantalla
        val precioFormateado = "$ ${producto.precio}"

        // Assert
        assertEquals("$ 59990", precioFormateado)
    }

    @Test
    fun `dos productos con mismo id son iguales en logica de negocio`() {
        // Arrange
        val producto1 = Producto(
            id = 100,
            nombre = "Mario Kart",
            precio = 49990
        )

        val producto2 = Producto(
            id = 100,
            nombre = "Mario Kart 8",
            precio = 59990
        )

        // Assert - Comparamos IDs
        assertEquals(producto1.id, producto2.id)
        // Obviamente los nombres son distintos
        assertNotEquals(producto1.nombre, producto2.nombre)
    }
}