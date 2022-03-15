package com.mariomanzano.nasa_explorer.usecases

import com.mariomanzano.nasa_explorer.data.repositories.DailyPicturesRepository

class GetPODUseCase(private val repository: DailyPicturesRepository) {

    operator fun invoke() = repository.podList
}