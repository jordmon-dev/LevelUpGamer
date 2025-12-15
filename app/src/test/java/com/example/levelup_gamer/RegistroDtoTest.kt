package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class RegistroDtoTest {

    @Test
    fun `registro dto contiene todos los campos correctamente`() {
        val registroDto = RegistroDto(
            nombre = "Juan",
            apellidos = "Pérez",
            email = "juan@test.com",
            password = "pass",
            direccion = "Calle 123",
            region = "Metro",
            comuna = "Centro"
        )

        assertEquals("Juan", registroDto.nombre)
        assertEquals("Pérez", registroDto.apellidos)
        assertEquals("juan@test.com", registroDto.email)
        assertEquals("pass", registroDto.password)
        assertEquals("Calle 123", registroDto.direccion)
        assertEquals("Metro", registroDto.region)
        assertEquals("Centro", registroDto.comuna)
    }
}