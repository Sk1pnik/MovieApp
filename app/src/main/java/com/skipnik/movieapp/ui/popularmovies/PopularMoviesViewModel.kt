package com.skipnik.movieapp.ui.popularmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skipnik.movieapp.data.MoviesRepository
import com.skipnik.movieapp.data.database.MovieDatabase
import com.skipnik.movieapp.data.database.MovieEntity
import com.skipnik.movieapp.utils.FetchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val db: MovieDatabase
) : ViewModel() {

    private val movieDao = db.movieDao()

    val movies = repository.getMovies(FetchState.Popular)

    fun addToFavorites(movie: MovieEntity)= viewModelScope.launch{
        when (movie.isFavorite) {
            true -> {
                movie.isFavorite = !movie.isFavorite
                movieDao.deleteMovie(movie)
            }
            false -> {
                movie.isFavorite = !movie.isFavorite
                movieDao.addMovie(movie)
            }
        }
    }

}