package com.skipnik.movieapp.ui.searchmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.skipnik.movieapp.data.MoviesRepository
import com.skipnik.movieapp.data.PreferenceManager
import com.skipnik.movieapp.data.database.MovieDatabase
import com.skipnik.movieapp.data.database.MovieEntity
import com.skipnik.movieapp.utils.FetchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val preferenceManager: PreferenceManager,
    private val db : MovieDatabase
) : ViewModel() {

    private val movieDao = db.movieDao()
    private val preferenceFlow = preferenceManager.preferencesFlow


    val movies = preferenceFlow.flatMapLatest { preferences ->
      repository.getMovies(FetchState.Search(preferences.movieName)).cachedIn(viewModelScope)
    }

    fun updateMovieQuery(newMovie: String) {
        viewModelScope.launch {
            preferenceManager.updateMovieQuery(newMovie)
        }
    }

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

