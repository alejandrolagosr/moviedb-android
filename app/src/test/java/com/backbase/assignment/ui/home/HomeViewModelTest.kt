package com.backbase.assignment.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.backbase.assignment.MainCoroutineRule
import com.flagos.domain.detail.model.MovieErrorItem
import com.flagos.domain.home.model.MoviesItem
import com.flagos.domain.home.model.NowPlayingItem
import com.flagos.domain.retrofit.NetworkResponse.*
import com.flagos.domain.usecase.MovieDbUseCase
import com.flagos.framework.home.usecase.MostPopularMoviesUseCase
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val movieDbUseCase = mock(MovieDbUseCase::class.java)
    private val mostPopularMoviesUseCase = mock(MostPopularMoviesUseCase::class.java)
    private val moviesItem = mock(MoviesItem::class.java)
    private val movieErrorItem = mock(MovieErrorItem::class.java)
    private val ioException = mock(IOException::class.java)
    private val throwable = mock(Throwable::class.java)

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(mostPopularMoviesUseCase, movieDbUseCase)
    }

    @Test
    fun `Given trying to fetch playing now movies, when returns Success, then show normal ui`() {
        //Given
        whenever(movieDbUseCase.getPlayingNowMovies()) doReturn flowOf(Success(moviesItem))
        moviesItem.stub {
            on { results } doReturn listOf(NowPlayingItem("path", 0))
        }

        //When
        homeViewModel.fetchPlayingNow()

        //Then
        assertThat(homeViewModel.onPlayingNowMoviesRetrieved.value, IsEqual(moviesItem.results))
    }

    @Test
    fun `Given trying to fetch playing now movies, when returns ApiError, then show error ui`() {
        //Given
        whenever(movieDbUseCase.getPlayingNowMovies()) doReturn flowOf(ApiError(movieErrorItem))

        //When
        homeViewModel.fetchPlayingNow()

        //Then
        assertThat(homeViewModel.onPlayingNowMoviesRetrieved.value, IsEqual(null))
    }

    @Test
    fun `Given trying to fetch playing now movies, when returns NetworkError, then show error ui`() {
        //Given
        whenever(movieDbUseCase.getPlayingNowMovies()) doReturn flowOf(NetworkError(ioException))

        //When
        homeViewModel.fetchPlayingNow()

        //Then
        assertThat(homeViewModel.onPlayingNowMoviesRetrieved.value, IsEqual(null))
    }

    @Test
    fun `Given trying to fetch playing now movies, when returns UnknownError, then show error ui`() {
        //Given
        whenever(movieDbUseCase.getPlayingNowMovies()) doReturn flowOf(UnknownError(throwable))

        //When
        homeViewModel.fetchPlayingNow()

        //Then
        assertThat(homeViewModel.onPlayingNowMoviesRetrieved.value, IsEqual(null))
    }
}
