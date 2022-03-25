package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository

class GetLastMarsUpdateDateUseCase(private val repository: LastDbUpdateRepository) {

    operator fun invoke() = repository.lastMarsUpdate
}