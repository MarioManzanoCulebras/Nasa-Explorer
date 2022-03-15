package com.mariomanzano.nasa_explorer.data.datasource

import arrow.core.Either
import com.mariomanzano.nasa_explorer.data.entities.Error
import com.mariomanzano.nasa_explorer.data.entities.MarsItem

interface MarsRemoteDataSource {
    suspend fun findMarsItems(): Either<Error, List<MarsItem>>
}