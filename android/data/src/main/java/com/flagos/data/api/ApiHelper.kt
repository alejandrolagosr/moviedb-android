package com.flagos.data.api

class ApiHelper(private val theMovieDbApi: MovieDbApi) {

    suspend fun getNowPlayingMovies() = theMovieDbApi.getNowPlayingMovies()

    suspend fun getMovieDetail(movieId: Int) = theMovieDbApi.getMovieDetail(movieId)
}
