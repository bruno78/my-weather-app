package com.brunogtavares.myweather.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class Favorite(
    @NonNull
    @PrimaryKey
    val city: String,
    val country: String,
)
