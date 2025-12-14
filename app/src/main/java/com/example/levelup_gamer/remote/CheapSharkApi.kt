package com.example.levelup_gamer.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// 👇 ESTA ES LA PARTE IMPORTANTE QUE TE FALTA 👇
data class ApiDeal(
    val title: String,
    val salePrice: String,
    val normalPrice: String,
    val savings: String,            // <--- Asegúrate de tener esta línea
    val thumb: String,
    val steamRatingPercent: String? // <--- Y esta línea (con el ?)
)
// 👆 --------------------------------------- 👆

interface CheapSharkService {
    @GET("api/1.0/deals")
    suspend fun getDeals(
        @Query("storeID") storeID: String = "1",
        @Query("pageSize") pageSize: Int = 15
    ): List<ApiDeal>
}

object ExternalRetrofit {
    private const val BASE_URL = "https://www.cheapshark.com/"

    val api: CheapSharkService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CheapSharkService::class.java)
    }
}