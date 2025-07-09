package com.example.movies.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import com.example.movies.R
import com.example.movies.databinding.FragmentHomeBinding
import com.example.movies.databinding.FragmentMovieBinding
import com.example.movies.model.Movie
import com.example.movies.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie) {

    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieBinding.bind(view)


        val movie = arguments?.getSerializable("movie") as? Movie
        movie?.let {
            binding.webView.apply {
                webViewClient = WebViewClient()
                it.url?.let { url -> loadUrl(url) }
            }

        }

    }
}