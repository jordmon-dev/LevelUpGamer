package com.example.levelup_gamer.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: UsuarioService by lazy {
        Retrofit.Builder()
            .baseUrl("10.0.2.2/localhost:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioService::class.java)
    }
}