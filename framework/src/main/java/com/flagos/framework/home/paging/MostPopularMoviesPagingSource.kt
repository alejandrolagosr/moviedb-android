package com.flagos.framework.home.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.flagos.data.repository.MostPopularMoviesRepositoryImpl
import com.flagos.framework.home.mapper.MostPopularMoviesUiMapper
import com.flagos.framework.home.model.MostPopularMovieItem

private const val ONE_PAGE = 1

class MostPopularMoviesPagingSource(
    private val movieDbRepository: MostPopularMoviesRepositoryImpl,
    private val mostPopularMoviesUiMapper: MostPopularMoviesUiMapper
) : PagingSource<Int, MostPopularMovieItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MostPopularMovieItem> {
        return try {
            val nextPage = params.key ?: ONE_PAGE
            val response = movieDbRepository.getMostPopularMovies(nextPage)

            LoadResult.Page(
                data = mostPopularMoviesUiMapper.toMostPopularItemList(response.results),
                prevKey = if (nextPage == ONE_PAGE) null else nextPage - ONE_PAGE,
                nextKey = response.page + ONE_PAGE
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MostPopularMovieItem>): Int? = state.anchorPosition
}
