package com.example.levelup_gamer.model

object DatosPrueba {
    val productos = listOf(
        Producto(
            id = 101,
            nombre = "God of War Ragnarök",
            precio = 59990,
            stock = 10,
            categoria = "PS5",
            descripcion = "Kratos y Atreus deben viajar a cada uno de los Nueve Reinos.",
            // Usamos URLs reales para que se vea bien
            imagen = "https://image.api.playstation.com/vulcan/ap/rnd/202207/1210/4xJ8XB3bi888QTLZYdl7Oi0s.png",
            codigo = "GOW-01"
        ),
        Producto(
            id = 102,
            nombre = "Mario Kart 8 Deluxe",
            precio = 45000,
            stock = 20,
            categoria = "Switch",
            descripcion = "Compite con tus amigos en la versión definitiva de Mario Kart.",
            imagen = "https://assets.nintendo.com/image/upload/ar_16:9,c_lpad,w_1240/b_white/f_auto/q_auto/ncom/software/switch/70010000000141/desc/0a3b3ebcc4594c96d5402604d23719f965d1d6a86f78f9f6d1c92762a4d0c1e8",
            codigo = "MK8-02"
        ),
        Producto(
            id = 103,
            nombre = "Elden Ring",
            precio = 64990,
            stock = 5,
            categoria = "Xbox",
            descripcion = "El nuevo juego de rol y acción de fantasía.",
            imagen = "https://image.api.playstation.com/vulcan/ap/rnd/202110/2000/phzKmw26046e72951900.png",
            codigo = "ER-03"
        ),
        Producto(
            id = 104,
            nombre = "Cyberpunk 2077",
            precio = 29990,
            stock = 15,
            categoria = "PC",
            descripcion = "Historia de acción y aventura en Night City.",
            imagen = "https://image.api.playstation.com/vulcan/ap/rnd/202311/2811/38d839659d54388137356230892019183416039589253406.png",
            codigo = "CP-04"
        ),
        Producto(
            id = 105,
            nombre = "Spider-Man 2",
            precio = 54990,
            stock = 8,
            categoria = "PS5",
            descripcion = "Los Spider-Men, Peter Parker y Miles Morales, regresan.",
            imagen = "https://image.api.playstation.com/vulcan/ap/rnd/202306/1219/60eca3ac155247e21850c7d075d01ebf0f3f5dbf19cc2aee.png",
            codigo = "SM-05"
        )
    )
}