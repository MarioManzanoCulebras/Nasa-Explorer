package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
import java.util.*
import javax.inject.Inject

class RequestPODSingleDayUseCase @Inject constructor(private val repository: DailyPicturesRepository) {

    suspend operator fun invoke(date: Calendar = Calendar.getInstance()) =
        repository.requestPODSingleDay(date)
}