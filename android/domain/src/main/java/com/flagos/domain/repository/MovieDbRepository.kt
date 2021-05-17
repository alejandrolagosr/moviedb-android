package com.flagos.domain.repository

import com.flagos.domain.detail.model.MovieDetailItem
import com.flagos.domain.detail.model.MovieErrorItem
import com.flagos.domain.home.model.MoviesItem
import com.flagos.domain.retrofit.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface MovieDbRepository {
    fun getMovieDetail(movieId: Int): Flow<NetworkResponse<MovieDetailItem, MovieErrorItem>>
    fun getPlayingNowMovies(): Flow<NetworkResponse<MoviesItem, MovieErrorItem>>
}
