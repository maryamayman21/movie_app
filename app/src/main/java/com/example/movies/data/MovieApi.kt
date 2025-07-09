package com.example.movies.data

import com.example.movies.model.MovieResponse
import com.example.movies.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface MovieApi {
    @GET(Constants.END_POINT)
    suspend fun getTvShows(): Response<MovieResponse>
}