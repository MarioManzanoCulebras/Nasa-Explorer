package com.mariomanzano.nasaexplorer.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NasaDao {

    @Query("SELECT * FROM DbPOD")
    fun getAllPOD(): Flow<List<DbPOD>>

    @Query("SELECT * FROM DbEarth")
    fun getAllEarth(): Flow<List<DbEarth>>

    @Query("SELECT * FROM DbMars")
    fun getAllMars(): Flow<List<DbMars>>

    @Query("SELECT * FROM DbPOD WHERE id = :id")
    fun findPODById(id: Int): Flow<DbPOD>

    @Query("SELECT * FROM DbEarth WHERE id = :id")
    fun findEarthById(id: Int): Flow<DbEarth>

    @Query("SELECT * FROM DbMars WHERE id = :id")
    fun findMarsById(id: Int): Flow<DbMars>

    @Query("SELECT COUNT(id) FROM DbPOD")
    suspend fun getPODCount(): Int

    @Query("SELECT COUNT(id) FROM DbEarth")
    suspend fun getEarthCount(): Int

    @Query("SELECT COUNT(id) FROM DbMars")
    suspend fun getMarsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPODEntities(movies: List<DbPOD>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEarthEntities(movies: List<DbEarth>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarsEntities(movies: List<DbMars>)
}