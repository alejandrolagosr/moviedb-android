package com.flagos.framework.home.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.flagos.data.repository.MovieDbRepository
import com.flagos.framework.home.mapper.MoviesUiMapper
import com.flagos.framework.home.model.MostPopularItem

private const val ONE_PAGE = 1

class MostPopularPagingSource(
    private val movieDbRepository: MovieDbRepository,
    private val moviesUiMapper: MoviesUiMapper
) : PagingSource<Int, MostPopularItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MostPopularItem> {
        return try {
            val nextPage = params.key ?: ONE_PAGE
            val response = movieDbRepository.getMostPopularMovies(nextPage)

            LoadResult.Page(
                data = moviesUiMapper.toMostPopularItemList(response.results),
                prevKey = if (nextPage == ONE_PAGE) null else nextPage - ONE_PAGE,
                nextKey = response.page + ONE_PAGE
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MostPopularItem>): Int? = state.anchorPosition
}
