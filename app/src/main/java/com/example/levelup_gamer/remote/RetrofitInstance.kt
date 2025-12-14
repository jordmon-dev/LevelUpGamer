package com.example.levelup_gamer.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    // URL CORRECTA para Spring Boot en local desde emulador
    // Asegúrate de incluir el /api/v1/ si tus controladores lo usan
    private const val BASE_URL = "http://10.0.2.2:8080/api/v1/"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Usaremos una sola interfaz para todo (Login, Registro, Productos)
    val api: LevelUpApiService by lazy {
        retrofit.create(LevelUpApiService::class.java)
    }
}