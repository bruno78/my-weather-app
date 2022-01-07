package com.brunogtavares.myweather.di

import android.content.Context
import androidx.room.Room
import com.brunogtavares.myweather.data.WeatherDao
import com.brunogtavares.myweather.data.WeatherDatabase
import com.brunogtavares.myweather.network.MyWeatherApi
import com.brunogtavares.myweather.repository.MyWeatherRepository
import com.brunogtavares.myweather.repository.MyWeatherRepositoryImpl
import com.brunogtavares.myweather.repository.WeatherDbRepository
import com.brunogtavares.myweather.repository.WeatherDbRepositoryImpl
import com.brunogtavares.myweather.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMyWeatherApi(): MyWeatherApi =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(MyWeatherApi::class.java)

    @Singleton
    @Provides
    fun provideMyWeatherRepository(api: MyWeatherApi): MyWeatherRepository =  MyWeatherRepositoryImpl(api)

    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase): WeatherDao =
        weatherDatabase.weatherDao()

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(context, WeatherDatabase::class.java, "weather_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideWeatherDbRepository(weatherDao: WeatherDao): WeatherDbRepository = WeatherDbRepositoryImpl(weatherDao)

}