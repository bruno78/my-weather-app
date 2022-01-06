package com.brunogtavares.myweather.repository

import androidx.room.*
import com.brunogtavares.myweather.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * from favorite_table")
    fun getFavorites(): Flow<List<Favorite>>

    @Query("SELECT * from favorite_table WHERE city =:city")
    suspend fun getFavoriteById(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(Favorite: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavorite(Favorite: Favorite)

    @Query("DELETE from favorite_table")
    suspend fun deleteAllFavorites()

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}