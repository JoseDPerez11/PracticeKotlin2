package com.dsa.practicekotlin2.domain

import com.dsa.practicekotlin2.domain.model.PredictionModel

interface Repository {
    suspend fun getPrediction(sign: String): PredictionModel?
}