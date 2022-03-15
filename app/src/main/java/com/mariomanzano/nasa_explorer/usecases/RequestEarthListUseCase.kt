package com.mariomanzano.nasa_explorer.usecases

import com.mariomanzano.nasa_explorer.data.repositories.DailyEarthRepository

class RequestEarthListUseCase(private val repository: DailyEarthRepository) {

    suspend operator fun invoke() = repository.requestEarthList()
}