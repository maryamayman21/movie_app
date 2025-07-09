package com.example.movies.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.model.Movie
import com.example.movies.model.MovieResponse
import com.example.movies.repository.MovieRepository
import com.example.movies.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject
constructor(private val repository: MovieRepository) : ViewModel() {

    private val _movieState = MutableLiveData<Resource<MovieResponse>>()
    val movieState: LiveData<Resource<MovieResponse>>
        get() = _movieState

    init {
        getAllTvShows()
    }

     fun getAllTvShows() = viewModelScope.launch {
        _movieState.value = Resource.Loading
        viewModelScope.launch {
            _movieState.value = repository.getTvShows()
        }
    }



}