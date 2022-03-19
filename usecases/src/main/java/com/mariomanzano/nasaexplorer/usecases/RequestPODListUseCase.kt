package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository

class RequestPODListUseCase(private val repository: DailyPicturesRepository) {

    suspend operator fun invoke(dayChanged: Boolean = false) = repository.requestPODList(dayChanged)
}