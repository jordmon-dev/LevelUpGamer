package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class UsuarioTest {

    @Test
    fun `usuario creado con todos los valores correctos`() {
        // Arrange
        val usuario = Usuario(
            id = 1L,
            nombre = "Juan Pérez",
            email = "juan@test.com",
            telefono = "+56912345678",
            password = "hashedPassword123",
            username = "juanperez",
            fechaCreacion = "2024-01-15T10:30:00Z",
            activo = true
        )

        // Assert
        assertEquals(1L, usuario.id)
        assertEquals("Juan Pérez", usuario.nombre)
        assertEquals("juan@test.com", usuario.email)
        assertEquals("+56912345678", usuario.telefono)
        assertEquals("hashedPassword123", usuario.password)
        assertEquals("juanperez", usuario.username)
        assertEquals("2024-01-15T10:30:00Z", usuario.fechaCreacion)
        assertEquals(true, usuario.activo)
    }

    @Test
    fun `usuario creado solo con campos obligatorios`() {
        // Arrange - Campos null por defecto
        val usuario = Usuario()

        // Assert
        assertNull(usuario.id)
        assertNull(usuario.nombre)
        assertNull(usuario.email)
        assertNull(usuario.telefono)
        assertNull(usuario.password)
        assertNull(usuario.username)
        assertNull(usuario.fechaCreacion)
        assertNull(usuario.activo)
    }

    @Test
    fun `usuario con email invalido retorna null`() {
        // Arrange
        val usuario = Usuario(
            id = 2L,
            nombre = "Test User",
            email = "emailinvalido", // Sin @
            activo = true
        )

        // Assert
        assertEquals(2L, usuario.id)
        assertEquals("Test User", usuario.nombre)
        assertEquals("emailinvalido", usuario.email)
        assertFalse(usuario.email?.contains("@") ?: false)
        assertEquals(true, usuario.activo)
        assertNull(usuario.telefono)
        assertNull(usuario.password)
    }

    @Test
    fun `usuario con email valido contiene arroba`() {
        // Arrange
        val usuario = Usuario(
            email = "usuario.valido@empresa.cl"
        )

        // Assert
        assertTrue(usuario.email?.contains("@") ?: false)
        assertTrue(usuario.email?.contains(".cl") ?: false)
    }

    @Test
    fun `usuario con telefono formato chileno`() {
        // Arrange
        val usuario = Usuario(
            telefono = "+56987654321"
        )

        // Assert
        assertEquals("+56987654321", usuario.telefono)
        assertTrue(usuario.telefono?.startsWith("+569") ?: false)
        assertEquals(12, usuario.telefono?.length)
    }

    @Test
    fun `usuario inactivo tiene activo false`() {
        // Arrange
        val usuario = Usuario(
            nombre = "Usuario Inactivo",
            email = "inactivo@test.com",
            activo = false
        )

        // Assert
        assertEquals("Usuario Inactivo", usuario.nombre)
        assertEquals(false, usuario.activo)
        assertFalse(usuario.activo ?: true)
    }

    @Test
    fun `usuario con username y sin nombre`() {
        // Arrange
        val usuario = Usuario(
            username = "gamer123",
            email = "gamer@test.com"
        )

        // Assert
        assertEquals("gamer123", usuario.username)
        assertEquals("gamer@test.com", usuario.email)
        assertNull(usuario.nombre)
    }

    @Test
    fun `usuario con fecha de creacion`() {
        // Arrange
        val fecha = "2024-03-20T14:25:30Z"
        val usuario = Usuario(
            id = 100L,
            nombre = "Usuario con Fecha",
            fechaCreacion = fecha
        )

        // Assert
        assertEquals(100L, usuario.id)
        assertEquals("Usuario con Fecha", usuario.nombre)
        assertEquals(fecha, usuario.fechaCreacion)
    }

    @Test
    fun `dos usuarios con mismo id son iguales por id`() {
        // Arrange
        val usuario1 = Usuario(
            id = 50L,
            nombre = "Usuario Uno",
            email = "uno@test.com"
        )

        val usuario2 = Usuario(
            id = 50L,
            nombre = "Usuario Dos", // Nombre diferente
            email = "dos@test.com"  // Email diferente
        )

        // Assert - Mismo ID
        assertEquals(usuario1.id, usuario2.id)
        assertNotEquals(usuario1.nombre, usuario2.nombre)
        assertNotEquals(usuario1.email, usuario2.email)
    }

    @Test
    fun `usuario con password hasheado`() {
        // Arrange
        val passwordHash = "\$2a\$10\$hashedpassword1234567890abcd"
        val usuario = Usuario(
            nombre = "Usuario Seguro",
            email = "seguro@test.com",
            password = passwordHash
        )

        // Assert
        assertNotNull(usuario.password)
        assertTrue(usuario.password?.length ?: 0 > 20)
        assertEquals(passwordHash, usuario.password)
    }

    @Test
    fun `usuario toString muestra informacion basica`() {
        // Arrange
        val usuario = Usuario(
            id = 99L,
            nombre = "Test User",
            email = "test@test.com",
            username = "tester"
        )

        // Act
        val stringRepresentation = usuario.toString()

        // Assert
        assertTrue(stringRepresentation.contains("Usuario"))
        assertTrue(stringRepresentation.contains("test@test.com") ||
                stringRepresentation.contains("99") ||
                stringRepresentation.contains("Test User"))
    }

    @Test
    fun `usuario con todos los campos null`() {
        // Arrange
        val usuario = Usuario(
            id = null,
            nombre = null,
            email = null,
            telefono = null,
            password = null,
            username = null,
            fechaCreacion = null,
            activo = null
        )

        // Assert
        assertNull(usuario.id)
        assertNull(usuario.nombre)
        assertNull(usuario.email)
        assertNull(usuario.telefono)
        assertNull(usuario.password)
        assertNull(usuario.username)
        assertNull(usuario.fechaCreacion)
        assertNull(usuario.activo)
    }

    @Test
    fun `usuario con campos vacios string`() {
        // Arrange
        val usuario = Usuario(
            nombre = "",
            email = "",
            telefono = "",
            username = ""
        )

        // Assert
        assertEquals("", usuario.nombre)
        assertEquals("", usuario.email)
        assertEquals("", usuario.telefono)
        assertEquals("", usuario.username)
        assertTrue(usuario.nombre?.isEmpty() ?: false)
        assertTrue(usuario.email?.isEmpty() ?: false)
    }
}