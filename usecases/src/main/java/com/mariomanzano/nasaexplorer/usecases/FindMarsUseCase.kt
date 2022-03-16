package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.nasaexplorer.repositories.MarsRepository
import kotlinx.coroutines.flow.Flow

class FindMarsUseCase(private val repository: MarsRepository) {

    operator fun invoke(id: Int): Flow<MarsItem> = repository.findById(id)
}