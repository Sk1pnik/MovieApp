package com.skipnik.movieapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skipnik.movieapp.api.MoviesApi
import com.skipnik.movieapp.data.databasemodel.MovieEntity
import com.skipnik.movieapp.data.networkmodel.movies.Movie
import com.skipnik.movieapp.data.networkmodel.movies.toDatabaseEntities
import retrofit2.HttpException
import java.io.IOException

private const val MOVIES_STARTING_PAGE_INDEX = 1

class MoviesPagingSource(
    private val moviesApi: MoviesApi
): PagingSource<Int, MovieEntity>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
       val position = params.key ?: MOVIES_STARTING_PAGE_INDEX

        return try {
            val genres = moviesApi.getGenres()
            val response = moviesApi.getTopRatedMovies(page = position)
            val movies = response.toDatabaseEntities(genres)

            LoadResult.Page(
                data = movies,
                prevKey = if (position == MOVIES_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if(movies.isEmpty()) null else position + 1
            )
        }catch (exception: IOException){
            LoadResult.Error(exception)
        }catch (exception: HttpException){
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