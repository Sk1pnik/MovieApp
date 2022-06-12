package com.skipnik.movieapp.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM movies WHERE id = :id)")
    fun isFavorite(id: Int): Boolean

    @Update
    suspend fun update(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteMovie(movie: MovieEntity)


}