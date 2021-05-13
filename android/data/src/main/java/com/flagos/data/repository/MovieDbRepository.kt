package com.flagos.data.repository

import com.flagos.data.api.ApiHelper

class MovieDbRepository(private val apiHelper: ApiHelper) {

    suspend fun getNowPlayingMovies() = apiHelper.getNowPlayingMovies()

    suspend fun getMostPopularMovies(page: Int) = apiHelper.getMostPopularMovies(page)

    suspend fun getMovieDetail(movieId: Int) = apiHelper.getMovieDetail(movieId)
}
