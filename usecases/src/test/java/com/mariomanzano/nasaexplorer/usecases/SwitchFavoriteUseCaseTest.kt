package com.mariomanzano.nasaexplorer.usecases

import com.mariomanzano.nasaexplorer.repositories.FavoritesRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SwitchFavoriteUseCaseTest {

    @Test
    fun `Invoke calls item repository`(): Unit = runBlocking {
        val favoritesRepository = mock<FavoritesRepository>()
        val switchFavoriteUseCase = SwitchFavoriteUseCase(favoritesRepository)
        val id = 1
        val type = "type"
        val favorite = true

        switchFavoriteUseCase(id, type, favorite)

        verify(favoritesRepository).switchFavorite(id, type, favorite)
    }
}