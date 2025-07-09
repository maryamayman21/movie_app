package com.example.movies.repository

import com.example.movies.data.ApiErrorHandler
import com.example.movies.data.AppError
import com.example.movies.data.MovieApi
import com.example.movies.data.NetworkMonitor
import com.example.movies.model.MovieResponse
import com.example.movies.utils.Resource
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import javax.inject.Inject

class MovieRepository @Inject
constructor(private val movieApi: MovieApi,
            private val networkMonitor: NetworkMonitor,
            private val errorHandler: ApiErrorHandler,
) {


   // suspend fun getTvShows() = movieApi.getTvShows()


    suspend fun getTvShows(): Resource<MovieResponse> {
        if (!networkMonitor.isOnline.first()) {

            return Resource.Error(AppError.NoInternetError)
        }

        return try {

            val response =  movieApi.getTvShows()
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!)
            }
            else {
                Resource.Error(errorHandler.handle(HttpException(response)))
            }
        } catch (e: Exception) {
            Resource.Error(errorHandler.handle(e))
        }
    }



}