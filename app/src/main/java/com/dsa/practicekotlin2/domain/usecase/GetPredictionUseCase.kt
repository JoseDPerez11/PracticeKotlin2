package com.dsa.practicekotlin2.domain.usecase

import com.dsa.practicekotlin2.domain.Repository
import javax.inject.Inject

class GetPredictionUseCase @Inject constructor(private val repository: Repository) {
        suspend operator fun invoke(sign: String) = repository.getPrediction(sign)
}