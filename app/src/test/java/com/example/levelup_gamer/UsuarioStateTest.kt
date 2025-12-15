package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class UsuarioStateTest {

    @Test
    fun `estado inicial tiene valores por defecto`() {
        val estado = UsuarioState()

        assertEquals("", estado.nombre)
        assertEquals("", estado.email)
        assertEquals("", estado.password)
        assertEquals("", estado.confirmPassword)
        assertFalse(estado.aceptarTerminos)
        assertEquals(UsuarioErrores(), estado.errores)
        assertEquals(1, estado.nivel)
        assertEquals(0, estado.puntosLevelUp)
    }

    @Test
    fun `copy crea nuevo estado con valores actualizados`() {
        val estadoOriginal = UsuarioState()
        val nuevoEstado = estadoOriginal.copy(
            nombre = "Juan",
            email = "juan@test.com",
            aceptarTerminos = true
        )

        assertEquals("Juan", nuevoEstado.nombre)
        assertEquals("juan@test.com", nuevoEstado.email)
        assertTrue(nuevoEstado.aceptarTerminos)
        // Los demás valores deben mantenerse igual
        assertEquals("", estadoOriginal.password)
        assertEquals(1, nuevoEstado.nivel)
    }
}