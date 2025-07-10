package com.example.movies.modules

import com.example.movies.data.ConnectivityMonitor
import com.example.movies.data.MovieApi
import com.example.movies.data.NetworkMonitor
import com.example.movies.model.Movie
import com.example.movies.model.MovieDeserializer
import com.example.movies.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
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
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideMMovieApi(retrofitProvider: Retrofit): MovieApi {
        return retrofitProvider.create(MovieApi::class.java)
    }

}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindNetworkMonitor(
        connectivityMonitor: ConnectivityMonitor
    ): NetworkMonitor
}

