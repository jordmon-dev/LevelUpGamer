package com.example.levelup_gamer.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // Usa 10.0.2.2 para el emulador de Android Studio (equivale a localhost de tu PC)
    // Si usas celular físico, pon la IP de tu PC (ej: 192.168.1.15)
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Aquí conectamos tu interfaz UsuarioService
    val api: UsuarioService by lazy {
        retrofit.create(UsuarioService::class.java)
    }
}