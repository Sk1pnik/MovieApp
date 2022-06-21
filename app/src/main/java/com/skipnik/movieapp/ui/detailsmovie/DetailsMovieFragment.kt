package com.skipnik.movieapp.ui.detailsmovie

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.skipnik.movieapp.R
import com.skipnik.movieapp.data.database.toMovieEntity
import com.skipnik.movieapp.databinding.FragmentMovieDetailsBinding
import com.skipnik.movieapp.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val TAG = "Android"

@AndroidEntryPoint
class DetailsMovieFragment : Fragment(R.layout.fragment_movie_details) {

    private val args: DetailsMovieFragmentArgs by navArgs()

    private val viewModel: DetailsMovieViewModel by viewModels()

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMovieDetailsBinding.bind(view)

        val navBar: BottomNavigationView = activity!!.findViewById(R.id.bottomNavigationView)
        navBar.visibility = View.GONE


        binding.apply {
            when (args.movie.isFavorite) {
                true -> movieFavorite.setImageResource(R.drawable.ic_bookmark_border_choosen)
                false -> movieFavorite.setImageResource(R.drawable.ic_bookmark_border)
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.movie(args.movie).collect { movie ->

                        movieFavorite.setOnClickListener {
                            movie.isFavorite = !movie.isFavorite

                            when (movie.isFavorite) {
                                true -> movieFavorite.setImageResource(R.drawable.ic_bookmark_border_choosen)
                                false -> movieFavorite.setImageResource(R.drawable.ic_bookmark_border)
                            }
                        }

                        movieGenres.text = movie.genres
                        movieVoteCount.text = movie.vote_count
                        movieRating.text = movie.vote_average
                        movieOverviewText.text = movie.overview
                        movieName.text = movie.title
                        movieReleaseDate.text = movie.release_date

                        Glide.with(this@DetailsMovieFragment)
                            .load("https://image.tmdb.org/t/p/w500/" + movie.backdrop_path)
                            .into(moviePost)

                        movieRuntime.text = movie.runtime.toString() + " " + "min"

                        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

                            if (movie.isFavorite != args.movie.isFavorite) {
                                viewModel.updateMovie(movie.toMovieEntity())
                            }

                            navBar.visibility = View.VISIBLE
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}