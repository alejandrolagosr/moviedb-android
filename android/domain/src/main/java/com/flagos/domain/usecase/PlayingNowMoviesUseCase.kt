package com.flagos.domain.usecase

import com.flagos.domain.repository.MovieDbRepository
import com.flagos.domain.detail.model.MovieDetailItem
import com.flagos.domain.detail.model.MovieErrorItem
import com.flagos.domain.home.model.NowPlayingItem
import com.flagos.domain.retrofit.NetworkResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PlayingNowMoviesUseCase (
    private val movieDbRepository: MovieDbRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getMovieDetail(movieId: Int): Flow<NetworkResponse<MovieDetailItem, MovieErrorItem>> {
        return movieDbRepository.getMovieDetail(movieId)
            .map { result ->
                when(result){
                    is NetworkResponse.Success -> NetworkResponse.Success(result.body)
                    is NetworkResponse.ApiError -> NetworkResponse.ApiError(result.body)
                    is NetworkResponse.NetworkError -> NetworkResponse.NetworkError(result.error)
                    is NetworkResponse.UnknownError -> NetworkResponse.UnknownError(result.error)
                }
            }
            .flowOn(dispatcher)
            .conflate()
    }

    fun getPlayingNowMovies(): Flow<List<NowPlayingItem>> {
        return movieDbRepository.getPlayingNowMovies().flowOn(dispatcher).conflate()
    }
}
