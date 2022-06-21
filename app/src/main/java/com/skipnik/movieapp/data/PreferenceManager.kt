package com.skipnik.movieapp.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "app_preferences")

@Singleton
class PreferenceManager @Inject constructor(@ApplicationContext context: Context) {

    private val preferencesDataStore = context.dataStore

    val preferencesFlow: Flow<MoviePreferences> = preferencesDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val movie = preferences[PreferencesKeys.MOVIE_NAME] ?: ""
            MoviePreferences(movie)
        }

    suspend fun updateMovieQuery(newMovie: String) {
        preferencesDataStore.edit { preferences ->
            preferences[PreferencesKeys.MOVIE_NAME] = newMovie
        }
    }


    private object PreferencesKeys {
        val MOVIE_NAME = stringPreferencesKey("movie_name")
    }


    data class MoviePreferences(
        val movieName: String,
    )


}
