package com.dsa.practicekotlin2.data

import android.util.Log
import com.dsa.practicekotlin2.data.network.HoroscopeApiService
import com.dsa.practicekotlin2.domain.Repository
import com.dsa.practicekotlin2.domain.model.PredictionModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor (private val apiService: HoroscopeApiService): Repository {
    override suspend fun getPrediction(sign: String): PredictionModel? {
        kotlin.runCatching { apiService.getHoroscope(sign) }
            .onSuccess { return it.toDomain() }
            .onFailure { Log.i("jose", "Ha ocurrido un error ${it.message}") }
        return null
    }
}