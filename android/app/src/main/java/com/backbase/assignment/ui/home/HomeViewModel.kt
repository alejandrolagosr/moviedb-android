package com.backbase.assignment.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.backbase.assignment.ui.home.mapper.MoviesUiMapper
import com.backbase.assignment.ui.home.model.MostPopularItem
import com.backbase.assignment.ui.home.model.MovieImageItem
import com.flagos.data.repository.MovieDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(
    private val movieDbRepository: MovieDbRepository,
    private val moviesUiMapper: MoviesUiMapper = MoviesUiMapper()
) : ViewModel() {

    private var _onPlayingNowMoviesRetrieved = MutableLiveData<List<MovieImageItem>>()
    val onPlayingNowMoviesRetrieved: LiveData<List<MovieImageItem>>
        get() = _onPlayingNowMoviesRetrieved

    private var _onMostPopularMoviesRetrieved = MutableLiveData<List<MostPopularItem>>()
    val onMostPopularMoviesRetrieved: LiveData<List<MostPopularItem>>
        get() = _onMostPopularMoviesRetrieved

    init {
        fetchPlayingNow()
        fetchMostPopular()
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

    private fun fetchMostPopular() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _onMostPopularMoviesRetrieved.postValue(
                    moviesUiMapper.toMostPopularItemList(
                        movieDbRepository.getMostPopularMovies().results
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
