package com.skipnik.movieapp.ui

import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skipnik.movieapp.data.databasemodel.MovieEntity
import com.skipnik.movieapp.data.networkmodel.movies.Movie
import com.skipnik.movieapp.databinding.MovieItemBinding

class MoviesAdapter :
    PagingDataAdapter<MovieEntity, MoviesAdapter.MovieViewHolder>(MOVIE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class MovieViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieEntity) {
            binding.apply {
                Glide.with(itemView)
                    .load("https://image.tmdb.org/t/p/w500/" + movie.poster_path)
                    .into(movieImage)

                movieName.text = movie.title
                movieReleaseDate.text = movie.release_date
                ratingStar.visibility = View.VISIBLE
                movieRating.text = movie.vote_average.toString()
                movieVoteCount.text = "(${movie.vote_count})"
                movieGenres.text = movie.genres.toString()
                    .replace("[", "")
                    .replace("]", "")

            }

        }
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<MovieEntity>() {

            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
                oldItem == newItem
        }
    }
}