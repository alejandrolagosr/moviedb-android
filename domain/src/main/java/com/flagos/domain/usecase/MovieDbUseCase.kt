package com.flagos.domain.usecase

import com.flagos.domain.repository.MovieDbRepository
import com.flagos.domain.detail.model.MovieDetailItem
import com.flagos.domain.detail.model.MovieErrorItem
import com.flagos.domain.home.model.MoviesItem
import com.flagos.domain.retrofit.NetworkResponse
import com.flagos.domain.retrofit.NetworkResponse.NetworkError
import com.flagos.domain.retrofit.NetworkResponse.ApiError
import com.flagos.domain.retrofit.NetworkResponse.UnknownError
import com.flagos.domain.retrofit.NetworkResponse.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MovieDbUseCase (
    private val movieDbRepository: MovieDbRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getMovieDetail(movieId: Int): Flow<NetworkResponse<MovieDetailItem, MovieErrorItem>> {
        return movieDbRepository.getMovieDetail(movieId)
            .map { result ->
                when(result){
                    is Success -> Success(result.body)
                    is ApiError -> ApiError(result.body)
                    is NetworkError -> NetworkError(result.error)
                    is UnknownError -> UnknownError(result.error)
                }
            }
            .flowOn(dispatcher)
            .conflate()
    }

    fun getPlayingNowMovies(): Flow<NetworkResponse<MoviesItem, MovieErrorItem>> {
        return movieDbRepository.getPlayingNowMovies()
            .map { result ->
                when(result){
                    is Success -> Success(result.body)
                    is ApiError -> ApiError(result.body)
                    is NetworkError -> NetworkError(result.error)
                    is UnknownError -> UnknownError(result.error)
                }
            }
            .flowOn(dispatcher)
            .conflate()
    }
}
