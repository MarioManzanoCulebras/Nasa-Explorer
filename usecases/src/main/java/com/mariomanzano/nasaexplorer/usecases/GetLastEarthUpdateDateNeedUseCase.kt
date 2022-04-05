package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository
import javax.inject.Inject

class GetLastEarthUpdateDateNeedUseCase @Inject constructor(private val repository: LastDbUpdateRepository) {

    operator fun invoke() = repository.lastEarthUpdate
}