package com.example.levelup_gamer.model

object DatosPrueba {
    val listaProductos = listOf(
        // 1. Cyberpunk
        Producto(
            id = 1,
            nombre = "Cyberpunk 2077",
            precio = 49990,
            stock = 20,
            categoria = "Futurista",
            descripcion = "Juego de rol de acción de mundo abierto ambientado en Night City.",
            imagen = "https://image.api.playstation.com/vulcan/ap/rnd/202311/2811/38d839659d54388137356230892019183416039589253406.png",
            codigo = "CYBER-01"
        ),
        // 2. Witcher
        Producto(
            id = 2,
            nombre = "The Witcher 3",
            precio = 29990,
            stock = 15,
            categoria = "RPG",
            descripcion = "Geralt de Rivia es un cazador de monstruos en un mundo devastado por la guerra.",
            imagen = "https://image.api.playstation.com/vulcan/ap/rnd/202211/0711/kh4MUIuMmHQUrE6NDh7Qr5sB.png",
            codigo = "WITCHER-02"
        ),
        // 3. RDR2
        Producto(
            id = 3,
            nombre = "Red Dead Redemption 2",
            precio = 39990,
            stock = 10,
            categoria = "Acción",
            descripcion = "Épica historia de la vida en el despiadado corazón de América.",
            imagen = "https://image.api.playstation.com/vulcan/ap/rnd/202010/2020/b4jR2584SSdDcc330d89260b37770857c5e5333f2e8276f7.png",
            codigo = "RDR2-03"
        ),
        // 4. TLOU2
        Producto(
            id = 4,
            nombre = "The Last of Us Part II",
            precio = 45990,
            stock = 8,
            categoria = "Aventura",
            descripcion = "Una historia compleja y emotiva sobre Ellie y Joel.",
            imagen = "https://image.api.playstation.com/vulcan/img/rnd/202010/2618/w48z6bzueZSPWOxww77IvHap.png",
            codigo = "TLOU2-04"
        ),
        // 5. Elden Ring
        Producto(
            id = 5,
            nombre = "Elden Ring",
            precio = 54990,
            stock = 12,
            categoria = "Souls",
            descripcion = "El nuevo juego de rol y acción de fantasía de FromSoftware.",
            imagen = "https://image.api.playstation.com/vulcan/ap/rnd/202110/2000/phzKmw26046e72951900.png",
            codigo = "ELDEN-05"
        ),
        // 6. GTA V
        Producto(
            id = 6,
            nombre = "Grand Theft Auto V",
            precio = 22990,
            stock = 50,
            categoria = "Acción",
            descripcion = "Un joven estafador callejero, un ladrón de bancos retirado y un psicópata.",
            imagen = "https://image.api.playstation.com/vulcan/ap/rnd/202206/1714/2257c24d9f6789125368a35607e6005720761e0892556736.png",
            codigo = "GTA5-06"
        ), // <--- ¡AQUÍ FALTABA LA COMA!

        // --- HARDWARE ---
        Producto(
            id = 7,
            nombre = "Consola PS5 Slim",
            precio = 499990,
            stock = 10,
            categoria = "Consolas",
            descripcion = "La nueva PS5 Slim con unidad de disco y gráficos 4K HDR.",
            imagen = "",
            codigo = "HW-PS5"
        ),
        Producto(
            id = 8,
            nombre = "Xbox Series X",
            precio = 489990,
            stock = 8,
            categoria = "Consolas",
            descripcion = "La Xbox más rápida y potente de la historia.",
            imagen = "",
            codigo = "HW-XBOX"
        ),
        Producto(
            id = 9,
            nombre = "Nintendo Switch OLED",
            precio = 349990,
            stock = 20,
            categoria = "Portátil",
            descripcion = "Pantalla OLED de 7 pulgadas, colores intensos y soporte ajustable.",
            imagen = "",
            codigo = "HW-NS"
        ),
        Producto(
            id = 10,
            nombre = "PC Gamer RTX 4070",
            precio = 1290000,
            stock = 3,
            categoria = "PC",
            descripcion = "Torre Gamer con i7, 32GB RAM y NVIDIA RTX 4070.",
            imagen = "",
            codigo = "HW-PC"
        ),
        Producto(
            id = 11,
            nombre = "Visor VR Pro",
            precio = 420000,
            stock = 5,
            categoria = "VR",
            descripcion = "Sumérgete en la realidad virtual con resolución 4K.",
            imagen = "",
            codigo = "HW-VR"
        ),
        Producto(
            id = 12,
            nombre = "Asus ROG Phone 8",
            precio = 890000,
            stock = 6,
            categoria = "Mobile",
            descripcion = "El rey del gaming móvil con pantalla de 165Hz.",
            imagen = "",
            codigo = "HW-MOB"
        )
    )
}