package com.example.levelup_gamer.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CarritoRetrofitInstance {

    // Cambia esta URL por la de tu API backend
    val api: CarritoApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CarritoApiService::class.java)
    }
}