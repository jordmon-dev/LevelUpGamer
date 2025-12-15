package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class UsuarioErroresTest {

    @Test
    fun `errores iniciales estan vacios`() {
        val errores = UsuarioErrores()

        assertEquals("", errores.nombre)
        assertEquals("", errores.email)
        assertEquals("", errores.password)
        assertEquals("", errores.confirmPassword)
        assertEquals("", errores.aceptaTerminos)
    }

    @Test
    fun `errores con mensajes se muestran correctamente`() {
        val errores = UsuarioErrores(
            nombre = "Nombre requerido",
            email = "Email inválido",
            password = "Mínimo 6 caracteres"
        )

        assertTrue(errores.nombre.isNotEmpty())
        assertTrue(errores.email.isNotEmpty())
        assertTrue(errores.password.isNotEmpty())

    }
}