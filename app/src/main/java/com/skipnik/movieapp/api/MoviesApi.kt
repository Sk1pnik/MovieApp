package com.skipnik.movieapp.api

import android.net.Uri
import com.skipnik.movieapp.BuildConfig
import com.skipnik.movieapp.data.networkmodel.moviedetails.MovieDetails
import com.skipnik.movieapp.data.networkmodel.movies.Result
import com.skipnik.movieapp.data.networkmodel.moviesgenre.Genres
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String = API_KEY
    ): Genres

    @GET("movie/popular")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): Result

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int,
        @Query("query") query: String
    ): Result

    @GET("movie/{movie_id}")
   suspend fun movieDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieDetails

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = BuildConfig.API_KEY
    }
}

