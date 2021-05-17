package com.backbase.assignment.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flagos.domain.detail.model.MovieDetailItem
import com.flagos.domain.retrofit.NetworkResponse.NetworkError
import com.flagos.domain.retrofit.NetworkResponse.ApiError
import com.flagos.domain.retrofit.NetworkResponse.UnknownError
import com.flagos.domain.retrofit.NetworkResponse.Success
import com.flagos.domain.usecase.PlayingNowMoviesUseCase
import com.backbase.assignment.ui.detail.DetailViewModel.MovieDetailUiState.OnShowError
import com.backbase.assignment.ui.detail.DetailViewModel.MovieDetailUiState.OnShowDetail
import com.backbase.assignment.ui.detail.DetailViewModel.MovieDetailUiState.OnShowLoading
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieId: Int,
    private val playingNowMoviesUseCase: PlayingNowMoviesUseCase
) : ViewModel() {

    private var _onMovieDetailStateChanged = MutableLiveData<MovieDetailUiState>()
    val onMovieDetailRetrieved: LiveData<MovieDetailUiState>
        get() = _onMovieDetailStateChanged

    init {
        fetchDetail()
    }

    fun fetchDetail() {
        viewModelScope.launch {
            playingNowMoviesUseCase.getMovieDetail(movieId)
                .onStart { _onMovieDetailStateChanged.value = OnShowLoading(true) }
                .onCompletion { _onMovieDetailStateChanged.value = OnShowLoading(false) }
                .collect { result ->
                    _onMovieDetailStateChanged.value = when (result) {
                        is Success -> OnShowDetail(result.body)
                        is ApiError -> OnShowError(result.body.status_message)
                        is NetworkError -> OnShowError(result.error.message.orEmpty())
                        is UnknownError -> OnShowError(result.error?.message.orEmpty())
                    }
                }
        }
    }

    sealed class MovieDetailUiState {
        data class OnShowDetail(val movieDetail: MovieDetailItem) : MovieDetailUiState()
        data class OnShowError(val message: String) : MovieDetailUiState()
        data class OnShowLoading(val showLoader: Boolean) : MovieDetailUiState()
    }
}
