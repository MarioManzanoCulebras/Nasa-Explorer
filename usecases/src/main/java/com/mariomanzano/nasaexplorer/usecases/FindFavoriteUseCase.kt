package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.domain.entities.NasaItem
import com.mariomanzano.nasaexplorer.repositories.FavoritesRepository
import kotlinx.coroutines.flow.Flow

class FindFavoriteUseCase(private val repository: FavoritesRepository) {

    operator fun invoke(id: Int, type: String): Flow<NasaItem> =
        repository.findByIdAndType(id, type)
}