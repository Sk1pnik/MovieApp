package com.skipnik.movieapp.ui.searchmovie

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
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
import com.skipnik.movieapp.ui.MoviesAdapter
import com.skipnik.movieapp.utils.onQueryTextSubmit
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchMovieFragment : Fragment(R.layout.fragment_movies), MoviesAdapter.OnItemClickListener {

    private val viewModel: SearchMovieViewModel by viewModels()

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchView: SearchView

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

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.fragment_menu, menu)

        val searchItem = menu.findItem(R.id.searchMovie)
        searchView = searchItem.actionView as SearchView

        searchView.onQueryTextSubmit {
            viewModel.updateMovieQuery(it)
            searchView.clearFocus()
        }
    }

    override fun onItemClick(movie: MovieEntity) {
        val action = SearchMovieFragmentDirections.actionSearchMovieFragmentToDetailsMovieFragment(
            movie,
            movie.title
        )
        findNavController().navigate(action)
    }

    override fun onFavoriteClick(movie: MovieEntity) {
        viewModel.addToFavorites(movie)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}