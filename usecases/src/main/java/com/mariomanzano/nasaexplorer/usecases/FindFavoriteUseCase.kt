package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.domain.entities.NasaItem
import com.mariomanzano.nasaexplorer.repositories.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import java.util.*

class FindFavoriteUseCase(private val repository: FavoritesRepository) {

    operator fun invoke(id: Int, date: Calendar): Flow<NasaItem> =
        repository.findByIdAndDate(id, date)
}