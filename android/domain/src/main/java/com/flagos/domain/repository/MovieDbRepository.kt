package com.flagos.domain.repository

import com.flagos.domain.detail.model.MovieDetailItem
import com.flagos.domain.home.model.NowPlayingItem
import kotlinx.coroutines.flow.Flow

interface MovieDbRepository {
    fun getMovieDetail(movieId: Int): Flow<MovieDetailItem>
    fun getPlayingNowMovies(): Flow<List<NowPlayingItem>>
}
