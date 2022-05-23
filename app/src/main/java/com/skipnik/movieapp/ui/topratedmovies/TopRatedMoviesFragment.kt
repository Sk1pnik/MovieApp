package com.skipnik.movieapp.ui.topratedmovies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.skipnik.movieapp.R
import com.skipnik.movieapp.databinding.FragmentMoviesBinding
import com.skipnik.movieapp.ui.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedMoviesFragment : Fragment(R.layout.fragment_movies) {

    private val viewModel: TopRatedMoviesViewModel by viewModels()

    private var _binding: FragmentMoviesBinding? = null
    val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMoviesBinding.bind(view)

        val moviesAdapter = MoviesAdapter()

        binding.apply {
            recyclerViewMovies.apply {
                adapter = moviesAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.movies.observe(viewLifecycleOwner) {
            moviesAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}