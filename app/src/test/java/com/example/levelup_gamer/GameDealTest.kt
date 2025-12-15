package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class GameDealTest {

    @Test
    fun `game deal se crea con todos los campos`() {
        val deal = GameDeal(
            title = "Elden Ring",
            salePrice = "40.00",
            normalPrice = "60.00",
            thumb = "img.jpg",
            steamRating = "95"
        )

        assertEquals("Elden Ring", deal.title)
        assertEquals("40.00", deal.salePrice)
        assertEquals("img.jpg", deal.thumb)
        assertEquals("95", deal.steamRating)
    }

    @Test
    fun `game deal usa valor por defecto para steamRating`() {
        // No pasamos steamRating, debe usar el default
        val deal = GameDeal(
            title = "Juego Indie",
            salePrice = "10.00",
            normalPrice = "10.00",
            thumb = "thumb.png"
        )

        assertEquals("0", deal.steamRating) // El valor por defecto en tu modelo es "0"
        assertEquals("Juego Indie", deal.title)
    }
}