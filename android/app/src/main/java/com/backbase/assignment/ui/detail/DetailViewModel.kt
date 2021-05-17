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
import com.flagos.domain.usecase.MovieDbUseCase
import com.backbase.assignment.ui.detail.DetailViewModel.MovieDetailUiState.OnShowError
import com.backbase.assignment.ui.detail.DetailViewModel.MovieDetailUiState.OnShowDetail
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailViewModel(
    private val movieId: Int,
    private val movieDbUseCase: MovieDbUseCase
) : ViewModel() {

    private var _onMovieDetailStateChanged = MutableLiveData<MovieDetailUiState>()
    val onMovieDetailRetrieved: LiveData<MovieDetailUiState>
        get() = _onMovieDetailStateChanged

    private var _onShowLoader = MutableLiveData<Boolean>()
    val onShowLoader: LiveData<Boolean>
        get() = _onShowLoader

    fun fetchDetail() {
        viewModelScope.launch {
            movieDbUseCase.getMovieDetail(movieId)
                .onStart { _onShowLoader.value = true }
                .onCompletion { _onShowLoader.value = false }
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
    }
}
