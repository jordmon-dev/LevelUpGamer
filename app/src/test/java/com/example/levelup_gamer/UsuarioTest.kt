package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class UsuarioTest {

    @Test
    fun `usuario creado con todos los valores correctos incluyendo rol`() {
        // Arrange
        val usuario = Usuario(
            id = 1L,
            nombre = "Admin User",
            email = "admin@test.com",
            rol = "ADMIN", // ✅ Probamos el nuevo campo
            activo = true
        )

        // Assert
        assertEquals(1L, usuario.id)
        assertEquals("Admin User", usuario.nombre)
        assertEquals("ADMIN", usuario.rol)
        assertEquals(true, usuario.activo)
    }

    @Test
    fun `usuario creado sin rol toma valor por defecto CLIENTE`() {
        // Arrange
        val usuario = Usuario(
            nombre = "Cliente Normal"
        )

        // Assert
        assertEquals("CLIENTE", usuario.rol) // ✅ Validamos el valor por defecto
        assertEquals("Cliente Normal", usuario.nombre)
    }

    // ... (Puedes dejar el resto de los tests antiguos aquí, seguirán funcionando) ...
}