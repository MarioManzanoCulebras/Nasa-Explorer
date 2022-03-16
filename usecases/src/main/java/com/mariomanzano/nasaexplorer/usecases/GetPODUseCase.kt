package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository

class GetPODUseCase(private val repository: DailyPicturesRepository) {

    operator fun invoke() = repository.podList
}