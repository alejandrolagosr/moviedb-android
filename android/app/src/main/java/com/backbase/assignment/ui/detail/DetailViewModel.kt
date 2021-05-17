package com.backbase.assignment.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flagos.domain.detail.model.MovieDetailItem
import com.flagos.domain.detail.model.MovieErrorItem
import com.flagos.domain.retrofit.NetworkResponse
import com.flagos.domain.retrofit.NetworkResponse.NetworkError
import com.flagos.domain.retrofit.NetworkResponse.ApiError
import com.flagos.domain.retrofit.NetworkResponse.UnknownError
import com.flagos.domain.retrofit.NetworkResponse.Success
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

    private var _onError = MutableLiveData<String>()
    val onError: LiveData<String>
        get() = _onError

    init {
        fetchDetail()
    }

    private fun fetchDetail(){
        viewModelScope.launch {
            playingNowMoviesUseCase.getMovieDetail(movieId)
                .onStart { /*TODO: Put a loader*/ }
                .onCompletion { /*TODO: Remove loader*/ }
                .collect { result ->
                    when(result) {
                        is Success ->  _onMovieDetailRetrieved.value = result.body
                        is ApiError -> _onError.value = result.body.status_message
                        is NetworkError -> _onError.value = result.error.message
                        is UnknownError -> _onError.value = result.error?.message
                    }
                }
        }
    }
}
