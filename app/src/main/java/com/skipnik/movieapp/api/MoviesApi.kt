package com.skipnik.movieapp.api

import com.skipnik.movieapp.BuildConfig
import com.skipnik.movieapp.data.networkmodel.movies.Result
import com.skipnik.movieapp.data.networkmodel.moviesgenre.Genres
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): Result

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String = API_KEY
    ): Genres

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = BuildConfig.API_KEY
    }
}