package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class LoginDtoTest {

    @Test
    fun `login dto serializa a JSON correctamente`() {
        val loginDto = LoginDto(
            email = "test@test.com",
            password = "123456"
        )

        assertEquals("test@test.com", loginDto.email)
        assertEquals("123456", loginDto.password)
    }

    @Test
    fun `login dto con campos vacios`() {
        val loginDto = LoginDto(
            email = "",
            password = ""
        )

        assertTrue(loginDto.email.isEmpty())
        assertTrue(loginDto.password.isEmpty())
    }
}