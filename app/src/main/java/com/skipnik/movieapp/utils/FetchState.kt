package com.skipnik.movieapp.utils

sealed class FetchState {
    object Popular: FetchState()
    data class Search(val searchQuery: String): FetchState()
}