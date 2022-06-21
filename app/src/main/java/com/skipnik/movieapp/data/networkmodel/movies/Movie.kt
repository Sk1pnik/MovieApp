package com.skipnik.movieapp.data.networkmodel.movies

import com.skipnik.movieapp.data.database.MovieEntity
import com.skipnik.movieapp.data.networkmodel.moviedetails.toMovieDetailsEntity
import com.skipnik.movieapp.data.networkmodel.moviesgenre.Genres
import com.skipnik.movieapp.utils.getGenres


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

fun Movie.toDatabaseEntity(
    genres: Genres,
    isFavorite: Boolean
): MovieEntity {
    return MovieEntity(
        genres = getGenres(this.genre_ids, genres).toString(),
        id = this.id,
        popularity = this.popularity,
        poster_path = this.poster_path,
        release_date = this.release_date,
        title = this.title,
        vote_average = String.format("%.2f", this.vote_average),
        vote_count = this.vote_count.toString(),
        isFavorite = isFavorite
    )

}



