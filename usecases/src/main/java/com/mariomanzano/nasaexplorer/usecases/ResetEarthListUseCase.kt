package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.DailyEarthRepository

class ResetEarthListUseCase(private val repository: DailyEarthRepository) {

    suspend operator fun invoke() = repository.resetEarthList()
}