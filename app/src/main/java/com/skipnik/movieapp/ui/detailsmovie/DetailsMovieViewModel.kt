package com.skipnik.movieapp.ui.detailsmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skipnik.movieapp.api.MoviesApi
import com.skipnik.movieapp.data.PreferenceManager
import com.skipnik.movieapp.data.database.MovieDatabase
import com.skipnik.movieapp.data.database.MovieDetailsEntity
import com.skipnik.movieapp.data.database.MovieEntity
import com.skipnik.movieapp.data.networkmodel.moviedetails.toMovieDetailsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsMovieViewModel @Inject constructor(
    private val moviesApi: MoviesApi,
    private val db: MovieDatabase
) : ViewModel() {

    private val movieDao = db.movieDao()

    fun movie(movie: MovieEntity): Flow<MovieDetailsEntity> = flow {
        emit(
            moviesApi.movieDetails(id = movie.id)
                .toMovieDetailsEntity(isFavorite = movie.isFavorite)
        )
    }

    fun updateMovie(movie: MovieEntity) = viewModelScope.launch {
        when (movie.isFavorite) {
            true -> {
                movieDao.addMovie(movie)
            }
            false -> {
                movieDao.deleteMovie(movie)
            }
        }
    }
}