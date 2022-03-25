package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.domain.entities.LastUpdateInfo
import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository

class UpdateLastMarsUpdateUseCase(private val repository: LastDbUpdateRepository) {

    suspend operator fun invoke(item: LastUpdateInfo) =
        repository.updateMarsLastUpdate(item)
}