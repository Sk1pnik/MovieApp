package com.skipnik.movieapp.data.networkmodel.movies

import com.skipnik.movieapp.data.databasemodel.MovieEntity
import com.skipnik.movieapp.data.networkmodel.moviesgenre.Genres

data class Movie(
    val adult: Boolean,
    val genre_ids: List<Int>,
    val id: Int,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
)


