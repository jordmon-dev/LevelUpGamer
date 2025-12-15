package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class UsuarioErroresTest {

    @Test
    fun `errores iniciales son strings vacios y no nulos`() {
        val errores = UsuarioErrores()

        // Tu modelo usa "" por defecto, no null
        assertEquals("", errores.nombre)
        assertEquals("", errores.email)
        assertEquals("", errores.password)
        assertEquals("", errores.confirmPassword)
        assertEquals("", errores.aceptaTerminos)
    }

    @Test
    fun `se pueden asignar mensajes de error`() {
        val errores = UsuarioErrores()

        // Como son 'var', podemos cambiarlos
        errores.email = "Email inválido"
        errores.password = "Muy corta"

        assertEquals("Email inválido", errores.email)
        assertEquals("Muy corta", errores.password)
        assertEquals("", errores.nombre) // Este sigue vacío
    }
}