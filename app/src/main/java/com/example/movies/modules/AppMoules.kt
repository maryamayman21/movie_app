package com.example.movies.modules

import com.example.movies.data.MovieApi
import com.example.movies.model.Movie
import com.example.movies.model.MovieDeserializer
import com.example.movies.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(Movie::class.java, MovieDeserializer())
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        BASE_URL: String,
        gson: Gson
    ): MovieApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(MovieApi::class.java)
}