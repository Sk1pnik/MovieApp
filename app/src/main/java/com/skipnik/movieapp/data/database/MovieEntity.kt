package com.skipnik.movieapp.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movies")
@Parcelize
data class MovieEntity(
    val genres: String,
    @PrimaryKey
    val id: Int,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String?,
    val title: String,
    val vote_average: String?,
    val vote_count: String?,
    var isFavorite: Boolean = false
): Parcelable