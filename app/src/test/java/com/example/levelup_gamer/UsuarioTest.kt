package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class UsuarioTest {

    @Test
    fun `usuario se crea correctamente con campos del backend`() {
        // Arrange
        val usuario = Usuario(
            id = 1L,
            nombre = "Jordy",
            apellidos = "Programador", // Nuevo
            email = "jordy@test.com",
            password = "pass",
            direccion = "Calle Viva 123", // Nuevo
            region = "RM",
            comuna = "Santiago",
            rol = "ADMIN", // Nuevo
            activo = true
        )

        // Assert
        assertEquals("Jordy", usuario.nombre)
        assertEquals("Programador", usuario.apellidos)
        assertEquals("Calle Viva 123", usuario.direccion)
        assertEquals("ADMIN", usuario.rol)
        // Verificamos que los campos eliminados ya no se testen
    }

    @Test
    fun `usuario rol por defecto es CLIENTE`() {
        val usuario = Usuario(
            nombre = "Nuevo",
            email = "nuevo@test.com",
            password = "123"
        )

        assertEquals("CLIENTE", usuario.rol)
    }

    @Test
    fun `usuario con direccion nula`() {
        val usuario = Usuario(
            nombre = "Sin Casa",
            direccion = null
        )
        assertNull(usuario.direccion)
    }
}