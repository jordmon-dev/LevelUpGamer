package com.example.levelup_gamer.model

import org.junit.Assert.*
import org.junit.Test

class GameDealTest {

    @Test
    fun `game deal creado correctamente con todos los campos`() {
        // Arrange
        val gameDeal = GameDeal(
            title = "Cyberpunk 2077",
            salePrice = "29.99",
            normalPrice = "59.99",
            thumb = "https://cdn.cheapshark.com/img/cyberpunk.jpg",
            steamRating = "87"
        )

        // Assert
        assertEquals("Cyberpunk 2077", gameDeal.title)
        assertEquals("29.99", gameDeal.salePrice)
        assertEquals("59.99", gameDeal.normalPrice)
        assertEquals("https://cdn.cheapshark.com/img/cyberpunk.jpg", gameDeal.thumb)
        assertEquals("87", gameDeal.steamRating)
    }

    @Test
    fun `game deal con steam rating por defecto cuando es null`() {
        // Arrange - steamRating es null, usa valor por defecto "0"
        val gameDeal = GameDeal(
            title = "Elden Ring",
            salePrice = "39.99",
            normalPrice = "49.99",
            thumb = "https://cdn.cheapshark.com/img/elden.jpg"
            // steamRating = null por defecto
        )

        // Assert
        assertEquals("Elden Ring", gameDeal.title)
        assertEquals("0", gameDeal.steamRating) // Valor por defecto
        assertEquals("39.99", gameDeal.salePrice)
    }

    @Test
    fun `game deal calcula descuento porcentual`() {
        // Arrange
        val gameDeal = GameDeal(
            title = "Test Game",
            salePrice = "20.00",
            normalPrice = "50.00",
            thumb = "test.jpg"
        )

        // Act - Calcular descuento
        val salePriceNum = gameDeal.salePrice.toDoubleOrNull() ?: 0.0
        val normalPriceNum = gameDeal.normalPrice.toDoubleOrNull() ?: 0.0
        val descuentoPorcentaje = ((normalPriceNum - salePriceNum) / normalPriceNum) * 100

        // Assert
        assertEquals(20.0, salePriceNum, 0.01)
        assertEquals(50.0, normalPriceNum, 0.01)
        assertEquals(60.0, descuentoPorcentaje, 0.01) // (50-20)/50 * 100 = 60%
    }

    @Test
    fun `game deal con steam rating alto`() {
        // Arrange
        val gameDeal = GameDeal(
            title = "Baldur's Gate 3",
            salePrice = "49.99",
            normalPrice = "59.99",
            thumb = "https://cdn.cheapshark.com/img/baldurs.jpg",
            steamRating = "96" // Rating muy alto
        )

        // Assert
        assertEquals("96", gameDeal.steamRating)
        val ratingNum = gameDeal.steamRating?.toIntOrNull()
        assertTrue(ratingNum != null && ratingNum > 90)
    }

    @Test
    fun `game deal con precios con formato diferente`() {
        // Arrange
        val gameDeal = GameDeal(
            title = "Game with Different Price Format",
            salePrice = "$19.99", // Con símbolo de dólar
            normalPrice = "49,99", // Con coma decimal
            thumb = "game.jpg"
        )

        // Assert - Los precios son Strings, pueden tener cualquier formato
        assertEquals("$19.99", gameDeal.salePrice)
        assertEquals("49,99", gameDeal.normalPrice)
        assertTrue(gameDeal.salePrice.contains("$"))
        assertTrue(gameDeal.normalPrice.contains(","))
    }

    @Test
    fun `game deal con titulo largo`() {
        // Arrange
        val tituloLargo = "The Legend of Zelda: Breath of the Wild - Nintendo Switch Edition"
        val gameDeal = GameDeal(
            title = tituloLargo,
            salePrice = "49.99",
            normalPrice = "69.99",
            thumb = "zelda.jpg"
        )

        // Assert
        assertEquals(tituloLargo, gameDeal.title)
        assertTrue(gameDeal.title.length > 30)
        assertTrue(gameDeal.title.contains("Zelda"))
    }

    @Test
    fun `game deal sin descuento`() {
        // Arrange - Mismo precio normal y de venta
        val gameDeal = GameDeal(
            title = "Game Full Price",
            salePrice = "59.99",
            normalPrice = "59.99",
            thumb = "fullprice.jpg"
        )

        // Act - Calcular si hay descuento
        val salePriceNum = gameDeal.salePrice.toDoubleOrNull() ?: 0.0
        val normalPriceNum = gameDeal.normalPrice.toDoubleOrNull() ?: 0.0
        val tieneDescuento = salePriceNum < normalPriceNum

        // Assert
        assertFalse(tieneDescuento)
        assertEquals(salePriceNum, normalPriceNum, 0.01)
    }

    @Test
    fun `game deal con steam rating no numerico`() {
        // Arrange
        val gameDeal = GameDeal(
            title = "Game No Rating",
            salePrice = "9.99",
            normalPrice = "19.99",
            thumb = "norating.jpg",
            steamRating = "N/A" // Rating no numérico
        )

        // Assert
        assertEquals("N/A", gameDeal.steamRating)
        val ratingNum = gameDeal.steamRating?.toIntOrNull()
        assertNull(ratingNum) // No se puede convertir a número
    }

    @Test
    fun `game deal con url de imagen valida`() {
        // Arrange
        val gameDeal = GameDeal(
            title = "Game with Image",
            salePrice = "14.99",
            normalPrice = "29.99",
            thumb = "https://example.com/games/game1.jpg?size=medium&quality=high"
        )

        // Assert
        assertTrue(gameDeal.thumb.startsWith("https://"))
        assertTrue(gameDeal.thumb.contains(".jpg"))
        assertTrue(gameDeal.thumb.contains("example.com"))
    }

    @Test
    fun `dos game deals iguales tienen mismos valores`() {
        // Arrange
        val deal1 = GameDeal(
            title = "Same Game",
            salePrice = "19.99",
            normalPrice = "39.99",
            thumb = "same.jpg",
            steamRating = "75"
        )

        val deal2 = GameDeal(
            title = "Same Game",
            salePrice = "19.99",
            normalPrice = "39.99",
            thumb = "same.jpg",
            steamRating = "75"
        )

        // Assert
        assertEquals(deal1.title, deal2.title)
        assertEquals(deal1.salePrice, deal2.salePrice)
        assertEquals(deal1.normalPrice, deal2.normalPrice)
        assertEquals(deal1.thumb, deal2.thumb)
        assertEquals(deal1.steamRating, deal2.steamRating)
    }

    @Test
    fun `game deal toString muestra informacion basica`() {
        // Arrange
        val gameDeal = GameDeal(
            title = "Test Game",
            salePrice = "29.99",
            normalPrice = "59.99",
            thumb = "test.jpg"
        )

        // Act
        val stringRepresentation = gameDeal.toString()

        // Assert
        assertTrue(stringRepresentation.contains("GameDeal"))
        assertTrue(stringRepresentation.contains("Test Game") ||
                stringRepresentation.contains("29.99"))
    }

    @Test
    fun `game deal con precios extremos`() {
        // Arrange
        val gameDeal = GameDeal(
            title = "Free Game",
            salePrice = "0.00", // Gratis
            normalPrice = "29.99",
            thumb = "free.jpg",
            steamRating = "80"
        )

        // Assert
        assertEquals("0.00", gameDeal.salePrice)
        val salePriceNum = gameDeal.salePrice.toDoubleOrNull()
        assertTrue(salePriceNum == 0.0)
    }
}