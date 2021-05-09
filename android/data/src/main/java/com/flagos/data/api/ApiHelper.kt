package com.flagos.data.api

class ApiHelper(private val theMovieDbApi: MovieDbApi) {

    suspend fun getNowPlayingMovies() = theMovieDbApi.getNowPlayingMovies()
}
