package com.flagos.data.api

import com.flagos.data.api.retrofit.MovieDbApi

class MovieDbClient(private val movieDbApi: MovieDbApi) {

    suspend fun getNowPlayingMovies() = movieDbApi.getNowPlayingMovies()

    suspend fun getMostPopularMovies(page: Int) = movieDbApi.getMostPopularMovies(page)

    suspend fun getMovieDetail(movieId: Int) = movieDbApi.getMovieDetail(movieId)
}
