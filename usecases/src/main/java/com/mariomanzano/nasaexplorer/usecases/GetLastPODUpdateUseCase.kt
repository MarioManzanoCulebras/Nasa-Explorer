package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository

class GetLastPODUpdateUseCase(private val repository: LastDbUpdateRepository) {

    operator fun invoke() = repository.lastPODUpdate
}