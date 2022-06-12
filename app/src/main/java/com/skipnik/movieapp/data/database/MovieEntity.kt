package com.skipnik.movieapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    val adult: Boolean,
    val genres: String,
    @PrimaryKey
    val id: Int,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String?,
    val title: String,
    val vote_average: Double?,
    val vote_count: Int?,
    var isFavorite: Boolean = false
)