package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository

class ResetPODListUseCase(private val repository: DailyPicturesRepository) {

    suspend operator fun invoke() = repository.resetPODList()
}