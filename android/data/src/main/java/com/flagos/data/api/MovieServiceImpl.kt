package com.flagos.data.api

import com.flagos.data.model.MovieDetailResponse
import com.flagos.data.model.MovieResponses
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieServiceImpl(private val movieDbClient: MovieDbClient): MovieService {

    override fun getMovieDetail(movieId: Int): Flow<MovieDetailResponse> = flow { emit(movieDbClient.getMovieDetail(movieId)) }

    override fun getPlayingNowMovies(): Flow<MovieResponses> = flow { emit(movieDbClient.getNowPlayingMovies()) }
}
