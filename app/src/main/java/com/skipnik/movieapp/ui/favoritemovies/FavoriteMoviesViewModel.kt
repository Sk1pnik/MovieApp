package com.skipnik.movieapp.ui.favoritemovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.skipnik.movieapp.data.MoviesRepository
import com.skipnik.movieapp.data.database.MovieDatabase
import com.skipnik.movieapp.data.database.MovieEntity
import com.skipnik.movieapp.utils.FetchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val db: MovieDatabase
) : ViewModel() {
    private val movieDao = db.movieDao()

    val movies = movieDao.getFavorites()

    fun deleteFromFavorites(movie: MovieEntity) = viewModelScope.launch {
        movie.isFavorite = !movie.isFavorite
        movieDao.deleteMovie(movie)
    }

}

