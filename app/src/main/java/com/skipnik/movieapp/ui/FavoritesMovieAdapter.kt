package com.skipnik.movieapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skipnik.movieapp.R
import com.skipnik.movieapp.data.database.MovieEntity
import com.skipnik.movieapp.databinding.MovieItemBinding

class FavoritesMovieAdapter(private val listener: OnItemClickListener) :
    ListAdapter<MovieEntity, FavoritesMovieAdapter.FavoritesMovieViewHolder>(
        FavoritesMovieComparator()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesMovieViewHolder {
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoritesMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesMovieViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {

            holder.bind(currentItem)
        }
    }

    inner class FavoritesMovieViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
//                root.setOnClickListener {
//                    val position = bindingAdapterPosition
//                    if (position != RecyclerView.NO_POSITION) {
//                        val task = getItem(position)
//                        if (task != null) {
//                            listener.onItemClick(task)
//                        }
//                    }
//                }
                movieFavorite.setOnClickListener {
                    val position = bindingAdapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val movie = getItem(position)
                        if (movie != null) {
                            listener.onFavoriteClick(movie)
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
                    true -> movieFavorite.setImageResource(R.drawable.ic_save_filled)
                    false -> movieFavorite.setImageResource(R.drawable.ic_save)
                }
            }

        }
    }

    class FavoritesMovieComparator : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity) =
            oldItem == newItem

    }

    interface OnItemClickListener {
        //        fun onItemClick(movie: MovieEntity)
        fun onFavoriteClick(movie: MovieEntity)
    }
}