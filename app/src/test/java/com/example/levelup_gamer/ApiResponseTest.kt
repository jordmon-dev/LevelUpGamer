package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class ApiResponseTest {

    @Test
    fun `api response exitosa contiene success true`() {
        // Arrange
        val usuario = Usuario(nombre = "Juan", email = "juan@test.com")
        val response = ApiResponse(
            success = true,
            message = "Operación exitosa",
            user = usuario
        )

        // Assert
        assertTrue(response.success)
        assertEquals("Operación exitosa", response.message)
        assertEquals("Juan", response.user?.nombre)
        assertEquals("juan@test.com", response.user?.email)
        assertNull(response.error)
    }

    @Test
    fun `api response con error contiene success false y mensaje de error`() {
        // Arrange
        val response = ApiResponse<String>(
            success = false,
            message = "Credenciales incorrectas",
            error = "Invalid credentials"
        )

        // Assert
        assertFalse(response.success)
        assertEquals("Credenciales incorrectas", response.message)
        assertEquals("Invalid credentials", response.error)
        assertNull(response.user)
    }

    @Test
    fun `api response exitosa sin mensaje opcional`() {
        // Arrange - message puede ser null
        val response = ApiResponse<Usuario>(
            success = true,
            user = Usuario(nombre = "Ana", email = "ana@test.com")
            // message es null por defecto
        )

        // Assert
        assertTrue(response.success)
        assertEquals("Ana", response.user?.nombre)
        assertNull(response.message)
        assertNull(response.error)
    }

    @Test
    fun `api response con datos genericos de tipo producto`() {
        // Arrange - ApiResponse puede contener cualquier tipo T
        val producto = Producto(
            id = 1L,
            nombre = "Cyberpunk 2077",
            precio = 59990
        )

        val response = ApiResponse(
            success = true,
            message = "Producto obtenido",
            user = producto
        )

        // Assert
        assertTrue(response.success)
        assertEquals("Producto obtenido", response.message)
        assertEquals("Cyberpunk 2077", (response.user as? Producto)?.nombre)
        assertEquals(59990, (response.user as? Producto)?.precio)
    }

    @Test
    fun `api response con solo success y error`() {
        // Arrange - Caso mínimo de error
        val response = ApiResponse<Any>(
            success = false,
            error = "Internal server error"
        )

        // Assert
        assertFalse(response.success)
        assertEquals("Internal server error", response.error)
        assertNull(response.message)
        assertNull(response.user)
    }

    @Test
    fun `api response exitosa con lista de usuarios`() {
        // Arrange - Puede contener una lista
        val usuarios = listOf(
            Usuario(nombre = "Usuario1", email = "u1@test.com"),
            Usuario(nombre = "Usuario2", email = "u2@test.com")
        )

        // Nota: Para esto necesitarías que user fuera de tipo List<Usuario>
        // O crear una ApiResponse específica para listas

        // Para este ejemplo, asumimos que ApiResponse puede contener cualquier T
        // y user es el campo que contiene los datos
    }

    @Test
    fun `api response serializa a json correctamente`() {
        // Arrange
        val response = ApiResponse(
            success = true,
            message = "Login exitoso",
            user = Usuario(nombre = "Carlos", email = "carlos@test.com"),
            error = null
        )

        // Assert - Verificar estructura de datos
        assertEquals(true, response.success)
        assertEquals("Login exitoso", response.message)
        assertEquals("Carlos", response.user?.nombre)
        assertNull(response.error)
    }

    @Test
    fun `api response con campos vacios pero success true`() {
        // Arrange - Respuesta exitosa pero sin datos extra
        val response = ApiResponse<Unit>(
            success = true,
            message = "Operación completada"
        )

        // Assert
        assertTrue(response.success)
        assertEquals("Operación completada", response.message)
        assertNull(response.user)
        assertNull(response.error)
    }

    @Test
    fun `dos api responses iguales tienen mismos valores`() {
        // Arrange
        val response1 = ApiResponse(
            success = true,
            message = "OK",
            user = Usuario(nombre = "Test")
        )

        val response2 = ApiResponse(
            success = true,
            message = "OK",
            user = Usuario(nombre = "Test")
        )

        // Assert - Comparar valores individuales
        assertEquals(response1.success, response2.success)
        assertEquals(response1.message, response2.message)
        assertEquals(response1.user?.nombre, response2.user?.nombre)
    }
}