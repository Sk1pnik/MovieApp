package com.skipnik.movieapp.data.networkmodel.moviedetails

import com.skipnik.movieapp.data.database.MovieDetailsEntity
import com.skipnik.movieapp.data.networkmodel.movies.toDatabaseEntity
import com.skipnik.movieapp.data.networkmodel.moviesgenre.Genre
import com.skipnik.movieapp.data.networkmodel.moviesgenre.Genres
import com.skipnik.movieapp.utils.getGenres

data class MovieDetails(
    val backdrop_path: String,
    val budget: Int,
    val genres: List<Genre>,
    val id: Int,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
)

fun MovieDetails.toMovieDetailsEntity(
    isFavorite: Boolean
): MovieDetailsEntity {
    return MovieDetailsEntity(
        genres = this.genres
            .map { it.name }
            .toString()
            .replace("[", "")
            .replace("]", ""),
        backdrop_path = this.backdrop_path,
        budget = this.budget,
        overview = this.overview,
        popularity = this.popularity,
        id = this.id,
        poster_path = this.poster_path,
        release_date = this.release_date,
        title = this.title,
        vote_average = String.format("%.2f", this.vote_average),
        vote_count = "(${this.vote_count})",
        isFavorite = isFavorite,
        revenue = this.revenue,
        runtime = this.runtime

    )
}