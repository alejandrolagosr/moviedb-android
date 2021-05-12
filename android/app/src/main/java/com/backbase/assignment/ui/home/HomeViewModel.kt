package com.backbase.assignment.ui.home

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.backbase.assignment.ui.home.mapper.MoviesUiMapper
import com.backbase.assignment.ui.home.model.MostPopularItem
import com.backbase.assignment.ui.home.model.MovieImageItem
import com.flagos.data.api.MovieDbApi
import com.flagos.data.repository.MovieDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.Exception

private const val PAGE_SIZE = 20
private const val PREFETCH_DISTANCE = 2

class HomeViewModel(
    private val movieDbApi: MovieDbApi,
    private val movieDbRepository: MovieDbRepository,
    private val moviesUiMapper: MoviesUiMapper = MoviesUiMapper()
) : ViewModel() {

    private var _onPlayingNowMoviesRetrieved = MutableLiveData<List<MovieImageItem>>()
    val onPlayingNowMoviesRetrieved: LiveData<List<MovieImageItem>>
        get() = _onPlayingNowMoviesRetrieved

    val movies: Flow<PagingData<MostPopularItem>> = Pager(PagingConfig(prefetchDistance = PREFETCH_DISTANCE, pageSize = PAGE_SIZE)) {
        MostPopularPagingSource(movieDbApi, moviesUiMapper)
    }.flow.cachedIn(viewModelScope)

    init {
        fetchPlayingNow()
    }

    private fun fetchPlayingNow() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _onPlayingNowMoviesRetrieved.postValue(
                    moviesUiMapper.toNowPlayingItemList(
                        movieDbRepository.getNowPlayingMovies().results
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        const val POSTER_PATH = "https://image.tmdb.org/t/p/original/"
    }
}
