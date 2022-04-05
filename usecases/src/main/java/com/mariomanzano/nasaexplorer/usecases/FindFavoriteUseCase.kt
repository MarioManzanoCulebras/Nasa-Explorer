package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.domain.entities.NasaItem
import com.mariomanzano.nasaexplorer.repositories.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FindFavoriteUseCase @Inject constructor(private val repository: FavoritesRepository) {

    operator fun invoke(id: Int, type: String): Flow<NasaItem> =
        repository.findByIdAndType(id, type)
}