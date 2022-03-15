package com.mariomanzano.nasa_explorer.usecases

import com.mariomanzano.nasa_explorer.data.entities.EarthItem
import com.mariomanzano.nasa_explorer.data.repositories.DailyEarthRepository
import kotlinx.coroutines.flow.Flow

class FindEarthUseCase(private val repository: DailyEarthRepository) {

    operator fun invoke(id: Int): Flow<EarthItem> = repository.findById(id)
}