package com.example.levelup_gamer.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    //  Agregamos "http://" y quitamos "localhost"
    //10.0.2.2
    //10.135.139.141
    //localhost
    private const val BASE_URL = "http://192.168.1.85:8080/api/v1/"

    val api: LevelUpApiService by lazy { // Usamos la nueva interfaz
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LevelUpApiService::class.java)
    }
}