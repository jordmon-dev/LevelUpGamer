package com.example.levelup_gamer.model

object DatosPrueba {
    val listaProductos = listOf(
        Producto(
            id = 1,
            nombre = "Elden Ring (Local)",
            precio = 45000,
            stock = 10,
            categoria = "RPG",
            imagen = "https://image.api.playstation.com/vulcan/ap/rnd/202110/2000/phvVT0qZfcRnJAY78l10bQQX.png",
            descripcion = "Juego offline de respaldo",
            codigo = "OFF-001"
        ),
        Producto(
            id = 2,
            nombre = "God of War (Local)",
            precio = 35000,
            stock = 5,
            categoria = "Acción",
            imagen = "https://image.api.playstation.com/vulcan/ap/rnd/202207/1210/4xJ8XB3bi888QTLZYdl7Oi0s.png",
            descripcion = "Respaldo local",
            codigo = "OFF-002"
        ),
        Producto(
            id = 3,
            nombre = "FIFA 24 (Local)",
            precio = 50000,
            stock = 20,
            categoria = "Deportes",
            imagen = "https://media.contentapi.ea.com/content/dam/ea/fc/fc-24/common/cover-athletes/fc24-standard-edition-cover-1x1.png.adapt.crop1x1.767p.png",
            descripcion = "Respaldo local",
            codigo = "OFF-003"
        )
    )
}