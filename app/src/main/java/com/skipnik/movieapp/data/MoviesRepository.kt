package com.skipnik.movieapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.skipnik.movieapp.api.MoviesApi
import com.skipnik.movieapp.data.database.MovieDao
import com.skipnik.movieapp.data.database.MovieDatabase
import com.skipnik.movieapp.data.database.MovieEntity
import com.skipnik.movieapp.utils.FetchState
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val moviesApi: MoviesApi,
    private val db: MovieDatabase
) {

    private val movieDao = db.movieDao()

    fun getMovies(state: FetchState) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSource(moviesApi, state, movieDao) }
        ).flow




}