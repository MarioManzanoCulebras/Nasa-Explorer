package com.mariomanzano.nasa_explorer.usecases

import com.mariomanzano.nasa_explorer.data.repositories.MarsRepository

class RequestMarsListUseCase(private val repository: MarsRepository) {

    suspend operator fun invoke() = repository.requestMarsList()
}