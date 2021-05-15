package com.flagos.data.api

import com.flagos.data.model.MovieDetailResponse
import com.flagos.data.model.MovieResponses
import kotlinx.coroutines.flow.Flow

interface MovieService {
    fun getMovieDetail(movieId: Int): Flow<MovieDetailResponse>
    fun getPlayingNowMovies(): Flow<MovieResponses>
}
