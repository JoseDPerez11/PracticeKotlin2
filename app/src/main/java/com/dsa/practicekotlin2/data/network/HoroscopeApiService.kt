package com.dsa.practicekotlin2.data.network

import com.dsa.practicekotlin2.data.network.response.PredictionResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HoroscopeApiService {

    @GET("/{sign}")
    suspend fun getHoroscope(@Path("sign") sign: String): PredictionResponse
}