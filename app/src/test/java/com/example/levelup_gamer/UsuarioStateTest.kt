package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class UsuarioStateTest {

    @Test
    fun `estado inicial tiene valores por defecto correctos`() {
        val estado = UsuarioState()

        // Verificamos los defaults definidos en tu modelo
        assertEquals("", estado.nombre)
        assertEquals("", estado.email)
        assertEquals("", estado.password)
        assertEquals(false, estado.aceptarTerminos)
        assertEquals(1, estado.nivel)          // Default es 1
        assertEquals(0, estado.puntosLevelUp)  // Default es 0
        assertEquals("", estado.fechaRegistro)
        assertNotNull(estado.errores)          // No debe ser null
    }

    @Test
    fun `copy crea nuevo estado manteniendo valores`() {
        val estadoOriginal = UsuarioState(nombre = "Original", nivel = 5)

        // Creamos una copia cambiando solo el email
        val nuevoEstado = estadoOriginal.copy(email = "nuevo@test.com")

        assertEquals("nuevo@test.com", nuevoEstado.email)
        assertEquals("Original", nuevoEstado.nombre) // Se mantiene
        assertEquals(5, nuevoEstado.nivel)           // Se mantiene
    }
}