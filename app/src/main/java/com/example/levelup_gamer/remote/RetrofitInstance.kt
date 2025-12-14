package com.example.levelup_gamer.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object RetrofitInstance {

    // 1. URL CORREGIDA: Apunta a tu Backend Spring Boot (incluye /api/v1/)
    private const val BASE_URL = "http://10.0.2.2:8080/api/v1/"

    // 2. Configuración del Cliente (Logs y Timeouts para que no falle lento)
    private val client: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // Muestra el JSON en consola
        }

        OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // 3. El constructor de Retrofit
    val api: LevelUpApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LevelUpApiService::class.java)
    }

    // 4. LA SOLUCIÓN AL ERROR: "usuarioService"
    // Creamos un alias. Cuando alguien pida usuarioService, le damos la api principal.
    // Esto hace que tu UsuarioViewModel deje de marcar error en rojo.
    val usuarioService: LevelUpApiService get() = api
}