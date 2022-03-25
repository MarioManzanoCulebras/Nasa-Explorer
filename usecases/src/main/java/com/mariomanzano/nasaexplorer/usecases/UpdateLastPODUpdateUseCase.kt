package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.domain.entities.LastUpdateInfo
import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository

class UpdateLastPODUpdateUseCase(private val repository: LastDbUpdateRepository) {

    suspend operator fun invoke(item: LastUpdateInfo?) =
        repository.updatePODLastUpdate(item)
}