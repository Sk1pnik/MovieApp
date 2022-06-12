package com.skipnik.movieapp.di

import android.app.Application
import androidx.room.Room
import com.skipnik.movieapp.api.MoviesApi
import com.skipnik.movieapp.data.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(MoviesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideMoviesApi(retrofit: Retrofit) : MoviesApi =
        retrofit.create(MoviesApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): MovieDatabase =
        Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "movie_database"
        ).build()

}