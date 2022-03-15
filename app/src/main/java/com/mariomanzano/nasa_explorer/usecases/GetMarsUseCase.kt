package com.mariomanzano.nasa_explorer.usecases

import com.mariomanzano.nasa_explorer.data.repositories.MarsRepository

class GetMarsUseCase(private val repository: MarsRepository) {

    operator fun invoke() = repository.marsList
}