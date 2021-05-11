package com.backbase.assignment.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backbase.assignment.ui.detail.mapper.MovieDetailUiMapper
import com.backbase.assignment.ui.detail.model.MovieDetailItem
import com.flagos.data.repository.MovieDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailViewModel(
    private val movieId: Int,
    private val movieDbRepository: MovieDbRepository,
    private val movieDetailUiMapper: MovieDetailUiMapper = MovieDetailUiMapper()
) : ViewModel() {

    private var _onMovieDetailRetrieved = MutableLiveData<MovieDetailItem>()
    val onMovieDetailRetrieved: LiveData<MovieDetailItem>
        get() = _onMovieDetailRetrieved

    init {
        fetchMovieDetail()
    }

    private fun fetchMovieDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _onMovieDetailRetrieved.postValue(movieDetailUiMapper.toMovieDetailItem(movieDbRepository.getMovieDetail(movieId)))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
