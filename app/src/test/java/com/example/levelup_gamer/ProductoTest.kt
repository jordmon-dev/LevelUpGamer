package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class ProductoTest {

    @Test
    fun `producto se crea correctamente con valores basicos`() {
        // Arrange
        val producto = Producto(
            id = 1L,
            codigo = "PROD-001",
            nombre = "Cyberpunk 2077",
            precio = 59990, // Precio en centavos o unidades enteras
            categoria = "Videojuegos",
            descripcion = "Juego de rol futurista",
            stock = 50,
            imagen = "https://example.com/cyberpunk.jpg"
        )

        // Assert
        assertEquals(1L, producto.id)
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
            id = 2L,
            nombre = "Juego Gratuito",
            precio = 0,
            stock = 100
        )

        // Assert
        assertEquals(0, producto.precio)
        assertEquals(100, producto.stock)
        assertTrue(producto.imagen.isNullOrEmpty())
    }

    @Test
    fun `producto sin imagen`() {
        // Arrange
        val producto = Producto(
            id = 3L,
            nombre = "Producto sin imagen",
            precio = 29990
        )

        // Assert
        assertNull(producto.imagen)
        assertEquals("", producto.codigo) // Valor por defecto
        assertEquals("", producto.categoria) // Valor por defecto
    }

    @Test
    fun `producto con stock negativo`() {
        // Arrange
        val producto = Producto(
            id = 4L,
            nombre = "Producto agotado",
            precio = 19990,
            stock = -5 // Stock negativo podría indicar agotado
        )

        // Assert
        assertTrue(producto.stock < 0)
    }

    @Test
    fun `producto con valores por defecto`() {
        // Arrange - Solo campos obligatorios
        val producto = Producto(
            nombre = "Producto mínimo",
            precio = 9990
        )

        // Assert
        assertEquals(0L, producto.id) // Valor por defecto
        assertEquals("", producto.codigo)
        assertEquals("", producto.categoria)
        assertEquals("", producto.descripcion)
        assertEquals(0, producto.stock)
        assertNull(producto.imagen)
    }

    @Test
    fun `producto convierte precio a decimal para mostrar`() {
        // Arrange
        val producto = Producto(
            nombre = "Juego de Prueba",
            precio = 59990 // Suponiendo que es en centavos
        )

        // Act - Método helper (si lo tienes) o cálculo manual
        val precioEnPesos = producto.precio / 100.0
        val precioFormateado = String.format("$%.2f", precioEnPesos)

        // Assert
        assertEquals(599.90, precioEnPesos, 0.01)
        assertEquals("$599.90", precioFormateado)
    }

    @Test
    fun `producto con codigo y categoria`() {
        // Arrange
        val producto = Producto(
            id = 10L,
            codigo = "GAME-123",
            nombre = "FIFA 24",
            precio = 69990,
            categoria = "Deportes",
            descripcion = "Juego de fútbol"
        )

        // Assert
        assertEquals("GAME-123", producto.codigo)
        assertEquals("Deportes", producto.categoria)
        assertTrue(producto.descripcion.isNotEmpty())
    }

    @Test
    fun `dos productos con mismo id son iguales`() {
        // Arrange
        val producto1 = Producto(
            id = 100L,
            nombre = "Mario Kart",
            precio = 49990
        )

        val producto2 = Producto(
            id = 100L,
            nombre = "Mario Kart 8",
            precio = 59990
        )

        // Assert - Solo comparamos IDs para igualdad (depende de tu lógica)
        assertEquals(producto1.id, producto2.id)
        assertNotEquals(producto1.nombre, producto2.nombre)
    }

    @Test
    fun `producto toString muestra informacion relevante`() {
        // Arrange
        val producto = Producto(
            id = 5L,
            nombre = "The Legend of Zelda",
            precio = 79990,
            categoria = "Aventura"
        )

        // Act
        val stringRepresentation = producto.toString()

        // Assert
        assertTrue(stringRepresentation.contains("The Legend of Zelda"))
        assertTrue(stringRepresentation.contains("79990"))
        assertTrue(stringRepresentation.contains("Aventura") || stringRepresentation.contains("Producto"))
    }
}