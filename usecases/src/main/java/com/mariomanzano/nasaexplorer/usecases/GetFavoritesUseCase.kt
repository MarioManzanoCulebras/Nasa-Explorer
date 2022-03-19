package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.FavoritesRepository

class GetFavoritesUseCase(private val repository: FavoritesRepository) {

    operator fun invoke() = repository.getList()
}