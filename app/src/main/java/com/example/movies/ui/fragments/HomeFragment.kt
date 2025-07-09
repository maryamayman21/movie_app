package com.example.movies.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.adapter.MovieAdapter
import com.example.movies.data.AppError
import com.example.movies.databinding.FragmentHomeBinding
import com.example.movies.databinding.ItemErrorBinding
import com.example.movies.model.MovieResponse
import com.example.movies.utils.Resource
import com.example.movies.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var _errorBinding: ItemErrorBinding? = null
    private val errorBinding get() = _errorBinding!!
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var adapter: MovieAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        _errorBinding = ItemErrorBinding.bind(binding.itemError.root)
        setupObservers()
        adapter = MovieAdapter()
        binding.rvTVShows.layoutManager = LinearLayoutManager(context)
        binding.rvTVShows.adapter = adapter



        adapter.setOnItemClickLisnter {
            val bundle = Bundle().apply {
                putSerializable("movie", it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_movieFragment, bundle)
        }

    }


    private fun setupObservers() {
        viewModel.movieState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> showLoading()
                is Resource.Success -> showMovies(state.data)
                is Resource.Error -> handleError(state.error)
                else -> {}
            }
        }
    }

    private fun showLoading() {

        binding.progressBar.visibility = View.VISIBLE
        binding.rvTVShows.visibility = View.GONE
    }

    private fun showMovies(data: MovieResponse) {
        binding.progressBar.visibility = View.GONE
        errorBinding.errorView.visibility = View.INVISIBLE
        adapter.submitList(data.movies?.toMutableList())
        binding.rvTVShows.visibility = View.VISIBLE
    }


    private fun handleError(error: AppError) {
        binding.progressBar.visibility = View.GONE
        errorBinding.errorView.visibility = View.VISIBLE

        val errorMessage = when (error) {
            is AppError.NoInternetError -> "No internet connection"
            is AppError.NotFoundError -> "Movie not found"
            else -> "Failed to load movies"
        }

        errorBinding.errorText.text = errorMessage
        errorBinding.retryButton.setOnClickListener {
            viewModel.getAllTvShows()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _errorBinding = null
    }
}