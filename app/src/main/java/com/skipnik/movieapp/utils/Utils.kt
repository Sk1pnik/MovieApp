package com.skipnik.movieapp.utils

import androidx.appcompat.widget.SearchView
import com.skipnik.movieapp.R
import com.skipnik.movieapp.data.networkmodel.moviesgenre.Genres

fun getGenres(genreIds: List<Int>, genres: Genres):List<String> {
    return genres.genres.filter {
        genreIds.contains(it.id)
    }.map {
        it.name
    }
}

inline fun SearchView.onQueryTextSubmit(crossinline listener: (String) -> Unit){
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            if (!query.isNullOrBlank()) {
                listener(query)
            }
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return true
        }
    })
}
