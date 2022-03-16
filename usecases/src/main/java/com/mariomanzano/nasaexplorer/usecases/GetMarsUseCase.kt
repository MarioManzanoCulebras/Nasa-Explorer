package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.MarsRepository

class GetMarsUseCase(private val repository: MarsRepository) {

    operator fun invoke() = repository.marsList
}