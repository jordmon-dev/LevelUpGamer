package com.example.levelup_gamer.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductoRetrofitInstance {

    // Cambia esta URL por la de tu API real de productos
    // Nota: Puedes usar la misma base URL si tu backend maneja ambos recursos
    val api: ProductoApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductoApiService::class.java)
    }
}