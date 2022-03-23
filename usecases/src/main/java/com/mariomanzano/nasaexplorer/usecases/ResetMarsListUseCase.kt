package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.MarsRepository

class ResetMarsListUseCase(private val repository: MarsRepository) {

    suspend operator fun invoke() = repository.resetMarsList()
}