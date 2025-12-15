package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class UsuarioTest {

    @Test
    fun `usuario creado con todos los valores correctos`() {
        // Arrange
        val usuario = Usuario(
            id = 1L, // ✅ Correcto: Usuario usa Long
            nombre = "Juan Pérez",
            email = "juan@test.com",
            telefono = "+56912345678",
            password = "hashedPassword123",
            username = "juanperez",
            fechaCreacion = "2024-01-15",
            activo = true
        )

        // Assert
        assertEquals(1L, usuario.id)
        assertEquals("Juan Pérez", usuario.nombre)
        assertEquals("juan@test.com", usuario.email)
        assertEquals("+56912345678", usuario.telefono)
        assertEquals("hashedPassword123", usuario.password)
        assertEquals("juanperez", usuario.username)
        assertEquals("2024-01-15", usuario.fechaCreacion)
        assertEquals(true, usuario.activo)
    }

    @Test
    fun `usuario creado solo con campos vacios o nulos`() {
        // Arrange - Constructor vacío usa nulos por defecto
        val usuario = Usuario()

        // Assert
        assertNull(usuario.id)
        assertNull(usuario.nombre)
        assertNull(usuario.email)
        assertNull(usuario.activo)
    }

    @Test
    fun `usuario copy funciona correctamente`() {
        // Arrange
        val usuarioOriginal = Usuario(id = 1L, nombre = "Original")

        // Act
        val usuarioCopia = usuarioOriginal.copy(nombre = "Copia")

        // Assert
        assertEquals(1L, usuarioCopia.id)
        assertEquals("Copia", usuarioCopia.nombre)
        assertEquals("Original", usuarioOriginal.nombre) // El original no cambia
    }
}