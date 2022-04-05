package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.MarsRepository
import javax.inject.Inject

class RequestMarsListUseCase @Inject constructor(private val repository: MarsRepository) {

    suspend operator fun invoke() = repository.requestMarsList()
}