package com.backbase.assignment.ui.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.flagos.framework.home.model.MostPopularMovieItem
import com.flagos.domain.home.model.NowPlayingItem
import com.flagos.domain.retrofit.NetworkResponse.Success
import com.flagos.domain.usecase.MovieDbUseCase
import com.flagos.framework.home.usecase.MostPopularMoviesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val mostPopularMoviesUseCase: MostPopularMoviesUseCase,
    private val playingNowMoviesUseCase: MovieDbUseCase,
) : ViewModel() {

    private var _onPlayingNowMoviesRetrieved = MutableLiveData<List<NowPlayingItem>>()
    val onPlayingNowMoviesRetrieved: LiveData<List<NowPlayingItem>>
        get() = _onPlayingNowMoviesRetrieved

    fun fetchPlayingNow() {
        viewModelScope.launch {
            playingNowMoviesUseCase.getPlayingNowMovies()
                .collect { result ->
                    if (result is Success) {
                        _onPlayingNowMoviesRetrieved.value = result.body.results
                    }
                }
        }
    }

    fun fetchMostPopular(): Flow<PagingData<MostPopularMovieItem>> {
        return mostPopularMoviesUseCase.getMostPopularMovies().cachedIn(viewModelScope)
    }

    companion object {
        const val POSTER_PATH = "https://image.tmdb.org/t/p/original/"
    }
}
