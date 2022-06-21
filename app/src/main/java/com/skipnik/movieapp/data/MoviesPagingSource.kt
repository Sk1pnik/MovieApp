package com.skipnik.movieapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skipnik.movieapp.api.MoviesApi
import com.skipnik.movieapp.data.database.MovieDao
import com.skipnik.movieapp.data.database.MovieDatabase
import com.skipnik.movieapp.data.database.MovieEntity
import com.skipnik.movieapp.data.networkmodel.movies.toDatabaseEntity
import com.skipnik.movieapp.utils.FetchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

private const val MOVIES_STARTING_PAGE_INDEX = 1


class MoviesPagingSource(
    private val moviesApi: MoviesApi,
    private val state: FetchState,
    private val movieDao: MovieDao
) : PagingSource<Int, MovieEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        val position = params.key ?: MOVIES_STARTING_PAGE_INDEX
        return try {
            val genres = moviesApi.getGenres()
            val result = when (state) {
                is FetchState.Search -> {
                    moviesApi.searchMovie(
                        page = position,
                        query = state.searchQuery
                    )
                }
                FetchState.Popular -> {
                    moviesApi.getTopRatedMovies(page = position)
                }
            }

            val movies = result.results.map {
                withContext(Dispatchers.IO) {
                    val isFavorite = movieDao.isFavorite(it.id)
                    it.toDatabaseEntity(genres, isFavorite)
                }
            }

            LoadResult.Page(
                data = movies,
                prevKey = if (position == MOVIES_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (movies.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}