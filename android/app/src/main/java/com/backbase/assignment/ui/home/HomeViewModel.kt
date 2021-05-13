package com.backbase.assignment.ui.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.flagos.framework.home.model.MostPopularItem
import com.flagos.framework.home.model.NowPlayingItem
import com.flagos.data.repository.MovieDbRepository
import com.flagos.framework.home.mapper.MoviesUiMapper
import com.flagos.framework.home.usecase.MovieDbUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(
    private val movieDbUseCase: MovieDbUseCase,
    private val movieDbRepository: MovieDbRepository,
    private val moviesUiMapper: MoviesUiMapper = MoviesUiMapper()
) : ViewModel() {

    private var _onPlayingNowMoviesRetrieved = MutableLiveData<List<NowPlayingItem>>()
    val onPlayingNowMoviesRetrieved: LiveData<List<NowPlayingItem>>
        get() = _onPlayingNowMoviesRetrieved

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

    fun fetchMostPopular(): Flow<PagingData<MostPopularItem>> = movieDbUseCase.getMostPopularMovies().cachedIn(viewModelScope)

    companion object {
        const val POSTER_PATH = "https://image.tmdb.org/t/p/original/"
    }
}
