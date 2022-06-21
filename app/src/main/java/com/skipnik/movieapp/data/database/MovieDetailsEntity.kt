package com.skipnik.movieapp.data.database


data class MovieDetailsEntity(
    val backdrop_path: String?,
    val budget: Int,
    val genres: String,
    val id: Int,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String?,
    val revenue: Int,
    val runtime: Int,
    val title: String,
    val vote_average: String?,
    val vote_count: String?,
    var isFavorite: Boolean
)

fun MovieDetailsEntity.toMovieEntity(): MovieEntity {
    return MovieEntity(
        genres = this.genres,
        id = this.id,
        popularity = this.popularity,
        poster_path = this.poster_path,
        release_date = this.release_date,
        title = this.title,
        vote_average = this.vote_average,
        vote_count = this.vote_count,
        isFavorite = isFavorite

    )
}


