package com.flagos.data.api

import com.flagos.data.api.retrofit.MovieService
import com.flagos.domain.detail.model.MovieDetailItem
import com.flagos.domain.detail.model.MovieErrorItem
import com.flagos.domain.home.model.MoviesItem
import com.flagos.domain.retrofit.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieServiceImpl(private val movieDbClient: MovieDbClient) : MovieService {

    override fun getMovieDetail(movieId: Int): Flow<NetworkResponse<MovieDetailItem, MovieErrorItem>> {
        return flow { emit(movieDbClient.getMovieDetail(movieId)) }
    }

    override fun getPlayingNowMovies(): Flow<NetworkResponse<MoviesItem, MovieErrorItem>> {
        return flow { emit(movieDbClient.getNowPlayingMovies()) }
    }
}
