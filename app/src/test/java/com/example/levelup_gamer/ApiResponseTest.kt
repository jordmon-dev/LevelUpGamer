package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class ApiResponseTest {

    @Test
    fun `api response exitosa guarda los datos correctamente`() {
        // Probamos con un String genérico para no depender de otros modelos
        val response = ApiResponse<String>(
            success = true,
            message = "OK",
            user = "Datos del usuario",
            error = null
        )

        assertTrue(response.success)
        assertEquals("OK", response.message)
        assertEquals("Datos del usuario", response.user)
        assertNull(response.error)
    }

    @Test
    fun `api response de error`() {
        val response = ApiResponse<String>(
            success = false,
            error = "Error de servidor"
        )

        assertFalse(response.success)
        assertEquals("Error de servidor", response.error)
        assertNull(response.user)
    }
}