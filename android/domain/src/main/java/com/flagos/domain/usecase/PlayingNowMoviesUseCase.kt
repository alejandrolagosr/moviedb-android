package com.flagos.domain.usecase

import com.flagos.domain.repository.MovieDbRepository
import com.flagos.domain.detail.model.MovieDetailItem
import com.flagos.domain.home.model.NowPlayingItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class PlayingNowMoviesUseCase (
    private val movieDbRepository: MovieDbRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getMovieDetail(movieId: Int): Flow<MovieDetailItem> {
        return movieDbRepository.getMovieDetail(movieId).flowOn(dispatcher).conflate()
    }

    fun getPlayingNowMovies(): Flow<List<NowPlayingItem>> {
        return movieDbRepository.getPlayingNowMovies().flowOn(dispatcher).conflate()
    }
}
