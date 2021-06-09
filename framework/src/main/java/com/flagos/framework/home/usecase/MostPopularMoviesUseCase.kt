package com.flagos.framework.home.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.flagos.data.repository.MostPopularMoviesRepositoryImpl
import com.flagos.framework.home.mapper.MostPopularMoviesUiMapper
import com.flagos.framework.home.model.MostPopularMovieItem
import com.flagos.framework.home.paging.MostPopularMoviesPagingSource
import kotlinx.coroutines.flow.Flow

private const val PAGE_SIZE = 20
private const val PREFETCH_DISTANCE = 2

class MostPopularMoviesUseCase(
    private val movieDbRepository: MostPopularMoviesRepositoryImpl,
    private val mostPopularMoviesUiMapper: MostPopularMoviesUiMapper = MostPopularMoviesUiMapper()
) {

    fun getMostPopularMovies(): Flow<PagingData<MostPopularMovieItem>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = PREFETCH_DISTANCE),
            pagingSourceFactory = { MostPopularMoviesPagingSource(movieDbRepository, mostPopularMoviesUiMapper) }
        ).flow
    }
}
