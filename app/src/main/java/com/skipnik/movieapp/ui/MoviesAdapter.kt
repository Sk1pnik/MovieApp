package com.skipnik.movieapp.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skipnik.movieapp.R
import com.skipnik.movieapp.data.database.MovieEntity
import com.skipnik.movieapp.databinding.MovieItemBinding

private val TAG = "Movie"

class MoviesAdapter(private val listener: OnItemClickListener) :
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

    inner class MovieViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val movie = getItem(position)
                        if (movie != null) {
                            listener.onItemClick( movie)
                        }
                    }
                }
                movieFavorite.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val movie = getItem(position)
                        if (movie != null) {
                            listener.onFavoriteClick(movie)
                            when (movie.isFavorite) {
                                true -> movieFavorite.setImageResource(R.drawable.ic_bookmark_border_choosen)
                                false -> movieFavorite.setImageResource(R.drawable.ic_bookmark_border)
                            }
                        }
                    }
                }
            }
        }


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
                movieGenres.text = movie.genres
                    .replace("[", "")
                    .replace("]", "")

                when (movie.isFavorite) {
                    true -> movieFavorite.setImageResource(R.drawable.ic_bookmark_border_choosen)
                    false -> movieFavorite.setImageResource(R.drawable.ic_bookmark_border)
                }
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

    interface OnItemClickListener {
        fun onItemClick( movie: MovieEntity)
        fun onFavoriteClick(movie: MovieEntity)
    }
}