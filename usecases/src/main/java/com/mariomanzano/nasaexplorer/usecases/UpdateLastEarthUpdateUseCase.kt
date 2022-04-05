package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.domain.entities.LastUpdateInfo
import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository
import javax.inject.Inject

class UpdateLastEarthUpdateUseCase @Inject constructor(private val repository: LastDbUpdateRepository) {

    suspend operator fun invoke(item: LastUpdateInfo) =
        repository.updateEarthLastUpdate(item)
}