package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.DailyEarthRepository
import javax.inject.Inject

class GetEarthUseCase @Inject constructor(private val repository: DailyEarthRepository) {

    operator fun invoke() = repository.earthList
}