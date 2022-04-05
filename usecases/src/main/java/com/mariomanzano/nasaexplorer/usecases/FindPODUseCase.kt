package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindPODUseCase @Inject constructor(private val repository: DailyPicturesRepository) {

    operator fun invoke(id: Int): Flow<PictureOfDayItem> = repository.findById(id)
}