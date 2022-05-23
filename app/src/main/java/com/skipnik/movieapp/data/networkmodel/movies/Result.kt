package com.skipnik.movieapp.data.networkmodel.movies

import com.skipnik.movieapp.data.databasemodel.MovieEntity
import com.skipnik.movieapp.data.networkmodel.moviesgenre.Genres
import com.skipnik.movieapp.utils.getGenres

data class Result(
    val results: List<Movie>
)

fun Result.toDatabaseEntities(genres: Genres): List<MovieEntity> {
    return results.map {
        MovieEntity(
            adult = it.adult,
            genres = getGenres(it.genre_ids, genres),
            id = it.id,
            popularity = it.popularity,
            poster_path = it.poster_path,
            release_date = it.release_date,
            title = it.title,
            vote_average = it.vote_average,
            vote_count = it.vote_count
        )
    }
}

