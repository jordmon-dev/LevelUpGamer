package com.example.levelup_gamer.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Este modelo recibe el JSON de Internet
data class ApiDeal(
    val title: String,
    val salePrice: String,
    val normalPrice: String,
    val savings: String,
    val thumb: String,
    val steamRatingPercent: String?
)

interface CheapSharkService {
    @GET("api/1.0/deals")
    suspend fun getDeals(
        @Query("storeID") storeID: String = "1", // 1 = Steam
        @Query("pageSize") pageSize: Int = 15
    ): List<ApiDeal>
}

// Singleton para la API Externa
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