package com.skipnik.movieapp.data.networkmodel.movies

import com.skipnik.movieapp.data.database.MovieDao
import com.skipnik.movieapp.data.database.MovieEntity
import com.skipnik.movieapp.data.networkmodel.moviesgenre.Genres
import com.skipnik.movieapp.utils.getGenres
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class Result(
    val results: List<Movie>
)

suspend fun Result.toDatabaseEntities(
    genres: Genres,
    movieDao: MovieDao
): List<MovieEntity> = withContext(Dispatchers.IO) {
    return@withContext results.map {
        MovieEntity(
            adult = it.adult,
            genres = getGenres(it.genre_ids, genres).toString(),
            id = it.id,
            popularity = it.popularity,
            poster_path = it.poster_path,
            release_date = it.release_date,
            title = it.title,
            vote_average = it.vote_average,
            vote_count = it.vote_count,
            isFavorite = movieDao.isFavorite(it.id)
        )
    }
}

