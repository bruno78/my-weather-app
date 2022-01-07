package com.brunogtavares.myweather.repository

import com.brunogtavares.myweather.data.WeatherDao
import com.brunogtavares.myweather.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface WeatherDbRepository {
    fun getFavorites(): Flow<List<Favorite>>
    suspend fun getFavoriteById(city: String): Favorite
    suspend fun addFavorite(favorite: Favorite)
    suspend fun updateFavorite(favorite: Favorite)
    suspend fun deleteAllFavorites()
    suspend fun deleteFavorite(favorite: Favorite)
}

class WeatherDbRepositoryImpl @Inject constructor(
    private val weatherDao: WeatherDao
) : WeatherDbRepository {

    override fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()

    override suspend fun getFavoriteById(city: String): Favorite =
        weatherDao.getFavoriteById(city)

    override suspend fun addFavorite(favorite: Favorite) {
        weatherDao.addFavorite(favorite)
    }

    override suspend fun updateFavorite(favorite: Favorite) {
        weatherDao.updateFavorite(favorite)
    }

    override suspend fun deleteAllFavorites() {
        weatherDao.deleteAllFavorites()
    }

    override suspend fun deleteFavorite(favorite: Favorite) {
        weatherDao.deleteFavorite(favorite)
    }

}