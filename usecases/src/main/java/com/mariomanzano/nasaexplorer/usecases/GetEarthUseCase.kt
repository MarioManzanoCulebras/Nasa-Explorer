package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.DailyEarthRepository

class GetEarthUseCase(private val repository: DailyEarthRepository) {

    operator fun invoke() = repository.earthList
}