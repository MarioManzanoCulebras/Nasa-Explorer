package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.FavoritesRepository

class SwitchFavoriteUseCase(private val repository: FavoritesRepository) {

    suspend operator fun invoke(id: Int, type: String, favorite: Boolean) =
        repository.switchFavorite(id, type, favorite)
}