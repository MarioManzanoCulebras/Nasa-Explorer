package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.MarsRepository

class RequestMarsListUseCase(private val repository: MarsRepository) {

    suspend operator fun invoke() = repository.requestMarsList()
}