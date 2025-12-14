package com.example.levelup_gamer.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// 1. Modelo de datos EXACTO de la API (Con los campos que te daban error)
data class ApiDeal(
    val title: String,
    val salePrice: String,
    val normalPrice: String,
    val savings: String,            // <--- Campo clave 1
    val thumb: String,
    val steamRatingPercent: String? // <--- Campo clave 2
)

// 2. Interfaz
interface CheapSharkService {
    @GET("api/1.0/deals")
    suspend fun getDeals(
        @Query("storeID") storeID: String = "1",
        @Query("pageSize") pageSize: Int = 15
    ): List<ApiDeal> // <--- Debe devolver lista de ApiDeal
}

// 3. Singleton (Reemplaza al archivo que borraste)
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