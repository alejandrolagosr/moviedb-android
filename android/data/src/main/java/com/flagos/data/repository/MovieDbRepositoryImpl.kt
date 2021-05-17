package com.flagos.data.repository

import com.flagos.data.api.retrofit.MovieService
import com.flagos.domain.repository.MovieDbRepository

class MovieDbRepositoryImpl(private val movieDbServices: MovieService): MovieDbRepository {

    override fun getMovieDetail(movieId: Int) = movieDbServices.getMovieDetail(movieId)

    override fun getPlayingNowMovies() = movieDbServices.getPlayingNowMovies()
}
