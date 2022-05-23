package com.skipnik.movieapp.data.databasemodel

import com.skipnik.movieapp.data.networkmodel.moviesgenre.Genres

data class MovieEntity(
    val adult: Boolean,
    val genres: List<String>,
    val id: Int,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
)