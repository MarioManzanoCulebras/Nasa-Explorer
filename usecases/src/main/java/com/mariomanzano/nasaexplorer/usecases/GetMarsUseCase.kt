package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.MarsRepository
import javax.inject.Inject

class GetMarsUseCase @Inject constructor(private val repository: MarsRepository) {

    operator fun invoke() = repository.marsList
}