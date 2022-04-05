package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.FavoritesRepository
import javax.inject.Inject

class SwitchFavoriteUseCase @Inject constructor(private val repository: FavoritesRepository) {

    suspend operator fun invoke(id: Int, type: String, favorite: Boolean) =
        repository.switchFavorite(id, type, favorite)
}