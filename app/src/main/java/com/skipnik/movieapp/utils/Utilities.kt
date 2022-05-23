package com.skipnik.movieapp.utils

import com.skipnik.movieapp.data.networkmodel.moviesgenre.Genres

fun getGenres(genreIds: List<Int>, genres: Genres):List<String> {
    return genres.genres.filter {
        genreIds.contains(it.id)
    }.map {
        it.name
    }
}