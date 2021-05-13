package com.flagos.framework.home.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.flagos.data.repository.MovieDbRepository
import com.flagos.framework.home.mapper.MoviesUiMapper
import com.flagos.framework.home.model.MostPopularItem
import com.flagos.framework.home.paging.MostPopularPagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

private const val PAGE_SIZE = 20
private const val PREFETCH_DISTANCE = 2

class MovieDbUseCase(
    private val movieDbRepository: MovieDbRepository,
    private val moviesUiMapper: MoviesUiMapper = MoviesUiMapper(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getMostPopularMovies(): Flow<PagingData<MostPopularItem>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, prefetchDistance = PREFETCH_DISTANCE),
            pagingSourceFactory = { MostPopularPagingSource(movieDbRepository, moviesUiMapper) }
        ).flow
    }
}
