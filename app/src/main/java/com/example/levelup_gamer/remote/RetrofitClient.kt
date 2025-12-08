package com.example.levelup_gamer.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Cliente Retrofit reutilizable en toda la app.
 * CAMBIA BASE_URL por la URL real de tu API.
 */
object RetrofitClient {

    // Ejemplo: backend en tu PC corriendo en el puerto 8080
    // Si usas emulador: 10.0.2.2 apunta a tu máquina host
    private const val BASE_URL = "http://10.0.2.2:8080/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging) // Log de peticiones/respuestas
        .build()

    val productoApi: ProductoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductoApiService::class.java)
    }
}
