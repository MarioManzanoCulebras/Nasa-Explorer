package com.mariomanzano.nasa_explorer.usecases

import com.mariomanzano.nasa_explorer.data.entities.MarsItem
import com.mariomanzano.nasa_explorer.data.repositories.MarsRepository
import kotlinx.coroutines.flow.Flow

class FindMarsUseCase(private val repository: MarsRepository) {

    operator fun invoke(id: Int): Flow<MarsItem> = repository.findById(id)
}