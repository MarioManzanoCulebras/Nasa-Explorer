package com.mariomanzano.nasa_explorer.usecases

import com.mariomanzano.nasa_explorer.data.repositories.DailyEarthRepository

class GetEarthUseCase(private val repository: DailyEarthRepository) {

    operator fun invoke() = repository.earthList
}