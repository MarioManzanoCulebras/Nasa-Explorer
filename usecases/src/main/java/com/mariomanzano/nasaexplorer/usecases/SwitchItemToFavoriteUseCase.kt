package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.domain.Error
import com.mariomanzano.domain.entities.EarthItem
import com.mariomanzano.domain.entities.MarsItem
import com.mariomanzano.domain.entities.NasaItem
import com.mariomanzano.domain.entities.PictureOfDayItem
import com.mariomanzano.nasaexplorer.repositories.DailyEarthRepository
import com.mariomanzano.nasaexplorer.repositories.DailyPicturesRepository
import com.mariomanzano.nasaexplorer.repositories.MarsRepository

class SwitchItemToFavoriteUseCase(
    private val dailyPicturesRepository: DailyPicturesRepository,
    private val dailyEarthRepository: DailyEarthRepository,
    private val marsRepository: MarsRepository
) {

    suspend operator fun invoke(item: NasaItem): Error? {
        return when (item) {
            is PictureOfDayItem -> {
                dailyPicturesRepository.switchFavorite(item)
            }
            is EarthItem -> {
                dailyEarthRepository.switchFavorite(item)
            }
            is MarsItem -> {
                marsRepository.switchFavorite(item)
            }
            else -> null
        }
    }
}