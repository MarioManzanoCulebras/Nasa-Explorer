package com.mariomanzano.nasa_explorer.usecases

import com.mariomanzano.nasa_explorer.data.entities.PictureOfDayItem
import com.mariomanzano.nasa_explorer.data.repositories.DailyPicturesRepository
import kotlinx.coroutines.flow.Flow

class FindPODUseCase(private val repository: DailyPicturesRepository) {

    operator fun invoke(id: Int): Flow<PictureOfDayItem> = repository.findById(id)
}