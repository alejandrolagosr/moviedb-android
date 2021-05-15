package com.flagos.data.repository

import com.flagos.data.api.MovieDbClient

class MostPopularMoviesRepositoryImpl(private val apiHelper: MovieDbClient) {

    suspend fun getMostPopularMovies(page: Int) = apiHelper.getMostPopularMovies(page)
}
