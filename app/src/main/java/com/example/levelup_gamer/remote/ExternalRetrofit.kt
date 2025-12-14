package com.example.levelup_gamer.remote

import com.example.levelup_gamer.model.GameDeal
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// 1. La Interfaz con los endpoints de CheapShark
interface CheapSharkApi {
    // Obtiene ofertas de la tienda 1 (Steam) con límite de 10 juegos
    @GET("api/1.0/deals")
    suspend fun getDeals(
        @Query("storeID") storeID: String = "1",
        @Query("pageSize") pageSize: Int = 10
    ): List<GameDeal>
}

// 2. La Instancia separada (Singleton)
object ExternalRetrofit {
    private const val BASE_URL = "https://www.cheapshark.com/"

    val api: CheapSharkApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CheapSharkApi::class.java)
    }
}