package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
import javax.inject.Inject

class RequestPODListUseCase @Inject constructor(private val repository: DailyPicturesRepository) {

    suspend operator fun invoke() =
        repository.requestPODList()
}