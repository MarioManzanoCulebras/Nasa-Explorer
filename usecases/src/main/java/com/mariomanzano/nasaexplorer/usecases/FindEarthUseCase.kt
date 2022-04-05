package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.nasaexplorer.repositories.DailyEarthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindEarthUseCase @Inject constructor(private val repository: DailyEarthRepository) {

    operator fun invoke(id: Int): Flow<EarthItem> = repository.findById(id)
}