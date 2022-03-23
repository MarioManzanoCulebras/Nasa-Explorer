package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.LastDbUpdateRepository
import java.util.*

class UpdateLastUpdateUseCase(private val repository: LastDbUpdateRepository) {

    suspend operator fun invoke(date: Calendar = Calendar.getInstance()) =
        repository.updatePODLastUpdate(date)
}