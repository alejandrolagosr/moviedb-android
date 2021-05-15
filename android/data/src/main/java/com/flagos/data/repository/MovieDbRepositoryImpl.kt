package com.flagos.data.repository

import com.flagos.data.mapper.MoviesDataMapper
import com.flagos.data.api.MovieService
import com.flagos.domain.repository.MovieDbRepository
import com.flagos.domain.detail.model.MovieDetailItem
import com.flagos.domain.home.model.NowPlayingItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieDbRepositoryImpl(
    private val movieDbServices: MovieService,
    private val moviesDataMapper: MoviesDataMapper = MoviesDataMapper()
): MovieDbRepository {

    override fun getMovieDetail(movieId: Int): Flow<MovieDetailItem> {
        return movieDbServices.getMovieDetail(movieId).map {
            moviesDataMapper.toMovieDetailItem(it)
        }
    }

    override fun getPlayingNowMovies(): Flow<List<NowPlayingItem>> {
        return movieDbServices.getPlayingNowMovies().map {
            moviesDataMapper.toNowPlayingItemList(it.results)
        }
    }
}
