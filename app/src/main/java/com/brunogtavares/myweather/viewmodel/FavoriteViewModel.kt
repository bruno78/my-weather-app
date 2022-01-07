package com.brunogtavares.myweather.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brunogtavares.myweather.model.Favorite
import com.brunogtavares.myweather.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: WeatherDbRepository
) : ViewModel() {
    private val _favoriteList = MutableStateFlow<List<Favorite>>(emptyList())
    val favoriteList = _favoriteList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged()
                .collect { values ->
                    if (values.isNullOrEmpty()) Log.d("FavoriteViewModel", "Repo returned an EMPTY LIST")
                    _favoriteList.value = values
                }
        }
    }
    fun insertFavorite(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) { repository.addFavorite(favorite) }
    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch(Dispatchers.IO) { repository.deleteFavorite(favorite) }
}