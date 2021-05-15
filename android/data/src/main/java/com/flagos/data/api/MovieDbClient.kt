package com.flagos.data.api

class MovieDbClient(private val movieDbApi: MovieDbApi) {

    suspend fun getNowPlayingMovies() = movieDbApi.getNowPlayingMovies()

    suspend fun getMostPopularMovies(page: Int) = movieDbApi.getMostPopularMovies(page)

    suspend fun getMovieDetail(movieId: Int) = movieDbApi.getMovieDetail(movieId)
}
