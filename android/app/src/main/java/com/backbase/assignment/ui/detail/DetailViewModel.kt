package com.backbase.assignment.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flagos.domain.detail.model.MovieDetailItem
import com.flagos.domain.usecase.PlayingNowMoviesUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieId: Int,
    private val playingNowMoviesUseCase: PlayingNowMoviesUseCase
) : ViewModel() {

    private var _onMovieDetailRetrieved = MutableLiveData<MovieDetailItem>()
    val onMovieDetailRetrieved: LiveData<MovieDetailItem>
        get() = _onMovieDetailRetrieved

    init {
        fetchDetail()
    }

    private fun fetchDetail(){
        viewModelScope.launch {
            playingNowMoviesUseCase.getMovieDetail(movieId)
                .onStart { /*TODO: Put a loader*/ }
                .onCompletion { /*TODO: Remove loader*/ }
                .collect { _onMovieDetailRetrieved.value = it }
        }
    }
}
