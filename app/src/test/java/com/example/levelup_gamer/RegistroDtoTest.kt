package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class RegistroDtoTest {

    @Test
    fun `registro dto contiene todos los campos correctamente`() {
        // Arrange
        val registroDto = RegistroDto(
            nombre = "Juan",
            apellidos = "Pérez González",
            email = "juan.perez@test.com",
            password = "password123",
            direccion = "Calle Falsa 123",
            region = "Metropolitana",
            comuna = "Santiago"
        )

        // Assert
        assertEquals("Juan", registroDto.nombre)
        assertEquals("Pérez González", registroDto.apellidos)
        assertEquals("juan.perez@test.com", registroDto.email)
        assertEquals("password123", registroDto.password)
        assertEquals("Calle Falsa 123", registroDto.direccion)
        assertEquals("Metropolitana", registroDto.region)
        assertEquals("Santiago", registroDto.comuna)
    }

    @Test
    fun `registro dto con campos minimos requeridos`() {
        // Arrange - Todos los campos son obligatorios (sin valores por defecto)
        val registroDto = RegistroDto(
            nombre = "Ana",
            apellidos = "López",
            email = "ana@test.com",
            password = "123456",
            direccion = "Av. Principal 456",
            region = "Valparaíso",
            comuna = "Viña del Mar"
        )

        // Assert
        assertTrue(registroDto.nombre.isNotBlank())
        assertTrue(registroDto.apellidos.isNotBlank())
        assertTrue(registroDto.email.contains("@"))
        assertTrue(registroDto.password.length >= 6)
        assertTrue(registroDto.direccion.isNotBlank())
        assertTrue(registroDto.region.isNotBlank())
        assertTrue(registroDto.comuna.isNotBlank())
    }

    @Test
    fun `registro dto con password de longitud exacta 6`() {
        // Arrange
        val registroDto = RegistroDto(
            nombre = "Carlos",
            apellidos = "Méndez",
            email = "carlos@test.com",
            password = "123456", // Exactamente 6 caracteres
            direccion = "Calle 789",
            region = "BioBío",
            comuna = "Concepción"
        )

        // Assert
        assertEquals(6, registroDto.password.length)
    }

    @Test
    fun `registro dto con email con formato correcto`() {
        // Arrange
        val registroDto = RegistroDto(
            nombre = "María",
            apellidos = "Rodríguez",
            email = "maria.rodriguez@empresa.cl",
            password = "securePass123",
            direccion = "Pasaje 101",
            region = "Araucanía",
            comuna = "Temuco"
        )

        // Assert
        assertTrue(registroDto.email.contains("@"))
        assertTrue(registroDto.email.contains("."))
    }

    @Test
    fun `registro dto con campos con espacios en blanco`() {
        // Arrange
        val registroDto = RegistroDto(
            nombre = "  Luis  ", // Con espacios
            apellidos = "  Fernández  ",
            email = "  luis@test.com  ",
            password = "  pass123  ",
            direccion = "  Calle con espacios  ",
            region = "  Los Lagos  ",
            comuna = "  Puerto Montt  "
        )

        // Assert - Los espacios son parte del valor
        assertTrue(registroDto.nombre.startsWith(" "))
        assertTrue(registroDto.nombre.endsWith(" "))
        assertTrue(registroDto.email.contains("@"))
    }

    @Test
    fun `registro dto con direccion larga`() {
        // Arrange
        val direccionLarga = "Avenida Siempre Viva 742, Departamento 4B, Torre Norte, Piso 8"

        val registroDto = RegistroDto(
            nombre = "Bart",
            apellidos = "Simpson",
            email = "bart@springfield.com",
            password = "eatmyshorts123",
            direccion = direccionLarga,
            region = "Springfield",
            comuna = "Centro"
        )

        // Assert
        assertTrue(registroDto.direccion.length > 30)
        assertEquals(direccionLarga, registroDto.direccion)
    }

    @Test
    fun `dos registro dtos iguales tienen mismos valores`() {
        // Arrange
        val dto1 = RegistroDto(
            nombre = "Pedro",
            apellidos = "Gómez",
            email = "pedro@test.com",
            password = "mypassword",
            direccion = "Calle 1",
            region = "Maule",
            comuna = "Talca"
        )

        val dto2 = RegistroDto(
            nombre = "Pedro",
            apellidos = "Gómez",
            email = "pedro@test.com",
            password = "mypassword",
            direccion = "Calle 1",
            region = "Maule",
            comuna = "Talca"
        )

        // Assert - Comparar todos los campos
        assertEquals(dto1.nombre, dto2.nombre)
        assertEquals(dto1.apellidos, dto2.apellidos)
        assertEquals(dto1.email, dto2.email)
        assertEquals(dto1.password, dto2.password)
        assertEquals(dto1.direccion, dto2.direccion)
        assertEquals(dto1.region, dto2.region)
        assertEquals(dto1.comuna, dto2.comuna)
    }

    @Test
    fun `registro dto con caracteres especiales en nombre`() {
        // Arrange
        val registroDto = RegistroDto(
            nombre = "José-María",
            apellidos = "Ñañez O'Higgins",
            email = "jose@test.com",
            password = "password123",
            direccion = "Calle Ñuñoa",
            region = "Metropolitana",
            comuna = "Ñuñoa"
        )

        // Assert
        assertTrue(registroDto.nombre.contains("-"))
        assertTrue(registroDto.apellidos.contains("Ñ"))
        assertTrue(registroDto.apellidos.contains("'"))
        assertTrue(registroDto.comuna.contains("Ñ"))
    }

    @Test
    fun `registro dto toString muestra informacion basica`() {
        // Arrange
        val registroDto = RegistroDto(
            nombre = "Test",
            apellidos = "User",
            email = "test@test.com",
            password = "secret",
            direccion = "Test 123",
            region = "Test",
            comuna = "Test"
        )

        // Act
        val stringRepresentation = registroDto.toString()

        // Assert
        assertTrue(stringRepresentation.contains("RegistroDto"))
        assertTrue(stringRepresentation.contains("test@test.com") ||
                stringRepresentation.contains("email"))
    }

    @Test
    fun `registro dto con password muy larga`() {
        // Arrange
        val passwordLarga = "a".repeat(100) // 100 caracteres

        val registroDto = RegistroDto(
            nombre = "Usuario",
            apellidos = "ConPassLargo",
            email = "user@test.com",
            password = passwordLarga,
            direccion = "Calle Larga",
            region = "Larga",
            comuna = "Larguísima"
        )

        // Assert
        assertEquals(100, registroDto.password.length)
        assertTrue(registroDto.password.length > 20)
    }
}