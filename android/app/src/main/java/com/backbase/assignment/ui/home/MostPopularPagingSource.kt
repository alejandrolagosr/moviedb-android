package com.backbase.assignment.ui.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.backbase.assignment.ui.home.mapper.MoviesUiMapper
import com.backbase.assignment.ui.home.model.MostPopularItem
import com.flagos.data.api.MovieDbApi

class MostPopularPagingSource(
    private val movieApiService: MovieDbApi,
    private val moviesUiMapper: MoviesUiMapper
) : PagingSource<Int, MostPopularItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MostPopularItem> {
        return try {
            val nextPage = params.key ?: 1
            val response = movieApiService.getMostPopularMovies(nextPage)

            LoadResult.Page(
                data = moviesUiMapper.toMostPopularItemList(response.results),
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = response.page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MostPopularItem>): Int? = state.anchorPosition
}
