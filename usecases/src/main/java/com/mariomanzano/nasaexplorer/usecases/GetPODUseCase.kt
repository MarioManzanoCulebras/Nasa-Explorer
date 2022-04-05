package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
import javax.inject.Inject

class GetPODUseCase @Inject constructor(private val repository: DailyPicturesRepository) {

    operator fun invoke() = repository.podList
}