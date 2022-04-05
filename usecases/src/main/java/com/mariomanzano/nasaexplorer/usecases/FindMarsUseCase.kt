package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.nasaexplorer.repositories.MarsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindMarsUseCase @Inject constructor(private val repository: MarsRepository) {

    operator fun invoke(id: Int): Flow<MarsItem> = repository.findById(id)
}