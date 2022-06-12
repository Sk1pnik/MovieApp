package com.skipnik.movieapp.ui.topratedmovies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.skipnik.movieapp.R
import com.skipnik.movieapp.data.database.MovieEntity
import com.skipnik.movieapp.databinding.FragmentMoviesBinding
import com.skipnik.movieapp.ui.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopRatedMoviesFragment : Fragment(R.layout.fragment_movies), MoviesAdapter.OnItemClickListener {

    private val viewModel: TopRatedMoviesViewModel by viewModels()

    private var _binding: FragmentMoviesBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMoviesBinding.bind(view)

        val moviesAdapter = MoviesAdapter(this)

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
                    moviesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                }
            }
        }
    }

    override fun onFavoriteClick(movie: MovieEntity) {

       viewModel.addToFavorites(movie)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}