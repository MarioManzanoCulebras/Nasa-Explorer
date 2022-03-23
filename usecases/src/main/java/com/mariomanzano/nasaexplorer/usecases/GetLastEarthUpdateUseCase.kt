package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository

class GetLastEarthUpdateUseCase(private val repository: LastDbUpdateRepository) {

    operator fun invoke() = repository.lastEarthUpdate
}