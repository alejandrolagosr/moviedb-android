package com.flagos.domain.usecase

import com.flagos.domain.detail.model.MovieDetailItem
import com.flagos.domain.detail.model.MovieErrorItem
import com.flagos.domain.repository.MovieDbRepository
import com.flagos.domain.retrofit.NetworkResponse
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDbUseCaseTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private val movieDbRepository = mock(MovieDbRepository::class.java)
    private val movieDetailItem = mock(MovieDetailItem::class.java)
    private val movieErrorItem = mock(MovieErrorItem::class.java)
    private val ioException = mock(IOException::class.java)
    private val throwable = mock(Throwable::class.java)
    private val movieId = 1324

    private lateinit var playingNowUseCase: MovieDbUseCase

    @Before
    fun setUp() {
        playingNowUseCase = MovieDbUseCase(movieDbRepository, testDispatcher)
    }

    @Test
    fun `Given movie detail return Success, when movie detail is called, then show normal ui`() = runBlockingTest(testDispatcher) {
        //Given
        whenever(movieDbRepository.getMovieDetail(movieId)).thenReturn(flowOf(NetworkResponse.Success(movieDetailItem)))

        //When
        val result = playingNowUseCase.getMovieDetail(movieId).first()

        //Then
        assertThat(result, IsInstanceOf(NetworkResponse.Success::class.java))
        assertThat((result as NetworkResponse.Success<*>).body, Is(equalTo(movieDetailItem)))
    }

    @Test
    fun `Given movie detail return ApiError, when movie detail is called, then show error`() = runBlockingTest(testDispatcher) {
        //Given
        whenever(movieDbRepository.getMovieDetail(movieId)).thenReturn(flowOf(NetworkResponse.ApiError(movieErrorItem)))

        //When
        val result = playingNowUseCase.getMovieDetail(movieId).first()

        //Then
        assertThat(result, IsInstanceOf(NetworkResponse.ApiError::class.java))
        assertThat((result as NetworkResponse.ApiError<*>).body, Is(equalTo(movieErrorItem)))
    }

    @Test
    fun `Given movie detail return NetworkError, when movie detail is called, then show error`() = runBlockingTest(testDispatcher) {
        //Given
        whenever(movieDbRepository.getMovieDetail(movieId)).thenReturn(flowOf(NetworkResponse.NetworkError(ioException)))

        //When
        val result = playingNowUseCase.getMovieDetail(movieId).first()

        //Then
        assertThat(result, IsInstanceOf(NetworkResponse.NetworkError::class.java))
        assertThat((result as NetworkResponse.NetworkError).error, Is(equalTo(ioException)))
    }

    @Test
    fun `Given movie detail return UnknownError, when movie detail is called, then show error`() = runBlockingTest(testDispatcher) {
        //Given
        whenever(movieDbRepository.getMovieDetail(movieId)).thenReturn(flowOf(NetworkResponse.UnknownError(throwable)))

        //When
        val result = playingNowUseCase.getMovieDetail(movieId).first()

        //Then
        assertThat(result, IsInstanceOf(NetworkResponse.UnknownError::class.java))
        assertThat((result as NetworkResponse.UnknownError).error, Is(equalTo(throwable)))
    }
}
