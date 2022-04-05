package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.DailyEarthRepository
import javax.inject.Inject

class RequestEarthListUseCase @Inject constructor(private val repository: DailyEarthRepository) {

    suspend operator fun invoke() = repository.requestEarthList()
}