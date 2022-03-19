package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.DailyEarthRepository

class RequestEarthListUseCase(private val repository: DailyEarthRepository) {

    suspend operator fun invoke(dayChanged: Boolean) = repository.requestEarthList(dayChanged)
}