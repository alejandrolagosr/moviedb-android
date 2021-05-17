package com.flagos.data.repository

import com.flagos.data.mapper.MoviesDataMapper
import com.flagos.data.api.retrofit.MovieService
import com.flagos.domain.repository.MovieDbRepository
import com.flagos.domain.detail.model.MovieDetailItem
import com.flagos.domain.detail.model.MovieErrorItem
import com.flagos.domain.home.model.NowPlayingItem
import com.flagos.domain.retrofit.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieDbRepositoryImpl(
    private val movieDbServices: MovieService,
    private val moviesDataMapper: MoviesDataMapper = MoviesDataMapper()
): MovieDbRepository {

    override fun getMovieDetail(movieId: Int) = movieDbServices.getMovieDetail(movieId)

    override fun getPlayingNowMovies(): Flow<List<NowPlayingItem>> {
        return movieDbServices.getPlayingNowMovies().map {
            moviesDataMapper.toNowPlayingItemList(it.results)
        }
    }
}
