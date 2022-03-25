package com.mariomanzano.nasaexplorer.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@Dao
interface NasaDao {

    @Query("SELECT * FROM DbPODLastUpdate")
    fun getPODLastUpdate(): Flow<DbPODLastUpdate?>

    @Query("SELECT * FROM DbEarthLastUpdate")
    fun getEarthLastUpdate(): Flow<DbEarthLastUpdate?>

    @Query("SELECT * FROM DbMarsLastUpdate")
    fun getMarsLastUpdate(): Flow<DbMarsLastUpdate?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePODDbLastUpdate(item: DbPODLastUpdate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateEarthDbLastUpdate(item: DbEarthLastUpdate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateMarsDbLastUpdate(item: DbMarsLastUpdate)

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

    @Delete
    suspend fun deletePOD(item: DbPOD)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPODOnDb(items: List<DbPOD>)

    @Transaction
    suspend fun insertPODEntities(items: List<DbPOD>) {
        getAllPOD().first { true }.also { list ->
            items.map { pod ->
                val element = list.find {
                    it.date == pod.date &&
                            it.title == pod.title &&
                            it.description == pod.description &&
                            it.url == pod.url &&
                            it.type == pod.type
                }
                if (element != null) {
                    deletePOD(element)
                }
            }
            insertPODOnDb(items)
        }
    }

    @Query("UPDATE DbPOD SET favorite = :favorite WHERE id = :id")
    suspend fun updatePODEntities(id: Int, favorite: Boolean)

    @Delete
    suspend fun deleteEarth(item: DbEarth)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEarthOnDb(items: List<DbEarth>)

    @Transaction
    suspend fun insertEarthEntities(items: List<DbEarth>) {
        getAllEarth().first { true }.also { list ->
            items.map { pod ->
                val element = list.find {
                    it.date == pod.date &&
                            it.title == pod.title &&
                            it.description == pod.description &&
                            it.url == pod.url &&
                            it.type == pod.type
                }
                if (element != null) {
                    deleteEarth(element)
                }
            }
            insertEarthOnDb(items)
        }
    }

    @Query("UPDATE DbEarth SET favorite = :favorite WHERE id = :id")
    suspend fun updateEarthEntities(id: Int, favorite: Boolean)

    @Delete
    suspend fun deleteMars(item: DbMars)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarsOnDb(items: List<DbMars>)

    @Transaction
    suspend fun insertMarsEntities(items: List<DbMars>) {
        getAllMars().first { true }.also { list ->
            items.map { pod ->
                val element = list.find {
                    it.date == pod.date &&
                            it.title == pod.title &&
                            it.description == pod.description &&
                            it.url == pod.url &&
                            it.type == pod.type
                }
                if (element != null) {
                    deleteMars(element)
                }
            }
            insertMarsOnDb(items)
        }
    }

    @Query("UPDATE DbMars SET favorite = :favorite WHERE id = :id")
    suspend fun updateMarsEntities(id: Int, favorite: Boolean)
}