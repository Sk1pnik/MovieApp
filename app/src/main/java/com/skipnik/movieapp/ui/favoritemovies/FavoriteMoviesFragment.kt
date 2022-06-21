package com.skipnik.movieapp.ui.favoritemovies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skipnik.movieapp.R
import com.skipnik.movieapp.data.database.MovieEntity
import com.skipnik.movieapp.databinding.FragmentMoviesBinding
import com.skipnik.movieapp.ui.FavoritesMovieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment(R.layout.fragment_movies),
    FavoritesMovieAdapter.OnItemClickListener {

    private val viewModel: FavoriteMoviesViewModel by viewModels()

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMoviesBinding.bind(view)

        val moviesAdapter = FavoritesMovieAdapter(this)

        binding.apply {
            recyclerViewMovies.apply {
                adapter = moviesAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movies.collect {
                    moviesAdapter.submitList(it)
                }
            }
        }
    }

    override fun onFavoriteClick(movie: MovieEntity) {

        viewModel.deleteFromFavorites(movie)
    }

    override fun onItemClick(movie: MovieEntity) {
        val action =
            FavoriteMoviesFragmentDirections.actionFavoriteMoviesFragmentToDetailsMovieFragment(
                movie,
                movie.title
            )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


