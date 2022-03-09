package com.mariomanzano.nasa_explorer.data.repositories

import com.mariomanzano.nasa_explorer.data.entities.NasaItem
import com.mariomanzano.nasa_explorer.data.entities.tryCall
import com.mariomanzano.nasa_explorer.data.entities.Result

abstract class Repository<T : NasaItem> {

    private var cache: List<T> = emptyList()

    internal suspend fun get(getAction: suspend () -> List<T>): Result<List<T>> = tryCall {
        if (cache.isEmpty()) {
            cache = getAction()
        }
        cache
    }

    internal suspend fun find(
        id: Int,
        findActionRemote: suspend () -> T
    ): Result<T> = tryCall {
        val character = cache.find { it.id == id }
        (character ?: findActionRemote())
    }
}