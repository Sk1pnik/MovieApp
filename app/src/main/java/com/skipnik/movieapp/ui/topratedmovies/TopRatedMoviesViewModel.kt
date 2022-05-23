package com.skipnik.movieapp.ui.topratedmovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.skipnik.movieapp.api.MoviesApi
import com.skipnik.movieapp.data.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject



@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
    repository: MoviesRepository
) : ViewModel() {

    val movies = repository.getPopularMovies().cachedIn(viewModelScope)

}