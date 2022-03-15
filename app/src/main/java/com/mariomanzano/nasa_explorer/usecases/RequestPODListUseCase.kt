package com.mariomanzano.nasa_explorer.usecases

import com.mariomanzano.nasa_explorer.data.repositories.DailyPicturesRepository

class RequestPODListUseCase(private val repository: DailyPicturesRepository) {

    suspend operator fun invoke() = repository.requestPODList()
}