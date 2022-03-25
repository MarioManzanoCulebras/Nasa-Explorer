package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository

class GetLastPODUpdateDateUseCase(private val repository: LastDbUpdateRepository) {

    operator fun invoke() = repository.lastPODUpdate
}