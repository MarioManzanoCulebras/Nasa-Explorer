package com.mariomanzano.nasaexplorer.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NasaDao {

    @Query("SELECT * FROM DbPODLastUpdate")
    fun getPODLastUpdate(): Flow<DbPODLastUpdate?>

    @Query("SELECT * FROM DbEarthLastUpdate")
    fun getEarthLastUpdate(): Flow<DbEarthLastUpdate?>

    @Query("SELECT * FROM DbMarsLastUpdate")
    fun getMarsLastUpdate(): Flow<DbMarsLastUpdate?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePODDbLastUpdate(date: DbPODLastUpdate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateEarthDbLastUpdate(date: DbEarthLastUpdate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMarsDbLastUpdate(date: DbMarsLastUpdate)

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
    suspend fun insertPODEntities(items: List<DbPOD>)

    @Query("UPDATE DbPOD SET favorite = :favorite WHERE id = :id")
    suspend fun updatePODEntities(id: Int, favorite: Boolean)

    @Query("DELETE FROM DbPOD WHERE favorite = :clearFavorites")
    suspend fun clearPODList(clearFavorites: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEarthEntities(items: List<DbEarth>)

    @Query("UPDATE DbEarth SET favorite = :favorite WHERE id = :id")
    suspend fun updateEarthEntities(id: Int, favorite: Boolean)

    @Query("DELETE FROM DbEarth WHERE favorite = :clearFavorites")
    suspend fun clearEarthList(clearFavorites: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarsEntities(items: List<DbMars>)

    @Query("UPDATE DbMars SET favorite = :favorite WHERE id = :id")
    suspend fun updateMarsEntities(id: Int, favorite: Boolean)

    @Query("DELETE FROM DbMars WHERE favorite = :clearFavorites")
    suspend fun clearMarsList(clearFavorites: Boolean)
}