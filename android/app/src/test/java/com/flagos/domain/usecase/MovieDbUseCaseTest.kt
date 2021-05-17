package com.flagos.domain.usecase

import com.flagos.domain.detail.model.MovieDetailItem
import com.flagos.domain.detail.model.MovieErrorItem
import com.flagos.domain.home.model.MoviesItem
import com.flagos.domain.repository.MovieDbRepository
import com.flagos.domain.retrofit.NetworkResponse
import com.flagos.domain.retrofit.NetworkResponse.*
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
    private val moviesItem = mock(MoviesItem::class.java)
    private val movieErrorItem = mock(MovieErrorItem::class.java)
    private val ioException = mock(IOException::class.java)
    private val throwable = mock(Throwable::class.java)

    private val movieId = 1324

    private lateinit var movieDbUseCase: MovieDbUseCase

    @Before
    fun setUp() {
        movieDbUseCase = MovieDbUseCase(movieDbRepository, testDispatcher)
    }

    @Test
    fun `Given movie detail return Success, when movie detail is called, then show normal ui`() = runBlockingTest(testDispatcher) {
        //Given
        whenever(movieDbRepository.getMovieDetail(movieId)).thenReturn(flowOf(Success(movieDetailItem)))

        //When
        val result = movieDbUseCase.getMovieDetail(movieId).first()

        //Then
        assertThat(result, IsInstanceOf(Success::class.java))
        assertThat((result as Success<*>).body, Is(equalTo(movieDetailItem)))
    }

    @Test
    fun `Given movie detail return ApiError, when movie detail is called, then show error`() = runBlockingTest(testDispatcher) {
        //Given
        whenever(movieDbRepository.getMovieDetail(movieId)).thenReturn(flowOf(ApiError(movieErrorItem)))

        //When
        val result = movieDbUseCase.getMovieDetail(movieId).first()

        //Then
        assertThat(result, IsInstanceOf(ApiError::class.java))
        assertThat((result as ApiError<*>).body, Is(equalTo(movieErrorItem)))
    }

    @Test
    fun `Given movie detail return NetworkError, when movie detail is called, then show error`() = runBlockingTest(testDispatcher) {
        //Given
        whenever(movieDbRepository.getMovieDetail(movieId)).thenReturn(flowOf(NetworkError(ioException)))

        //When
        val result = movieDbUseCase.getMovieDetail(movieId).first()

        //Then
        assertThat(result, IsInstanceOf(NetworkError::class.java))
        assertThat((result as NetworkError).error, Is(equalTo(ioException)))
    }

    @Test
    fun `Given movie detail return UnknownError, when movie detail is called, then show error`() = runBlockingTest(testDispatcher) {
        //Given
        whenever(movieDbRepository.getMovieDetail(movieId)).thenReturn(flowOf(UnknownError(throwable)))

        //When
        val result = movieDbUseCase.getMovieDetail(movieId).first()

        //Then
        assertThat(result, IsInstanceOf(UnknownError::class.java))
        assertThat((result as UnknownError).error, Is(equalTo(throwable)))
    }

    @Test
    fun `Given playing now movies return Success, when playing now is called, then show normal ui`() = runBlockingTest(testDispatcher) {
        //Given
        whenever(movieDbRepository.getPlayingNowMovies()).thenReturn(flowOf(Success(moviesItem)))

        //When
        val result = movieDbUseCase.getPlayingNowMovies().first()

        //Then
        assertThat(result, IsInstanceOf(Success::class.java))
        assertThat((result as Success<*>).body, Is(equalTo(moviesItem)))
    }

    @Test
    fun `Given playing now movies return ApiError, when movie detail is called, then show error`() = runBlockingTest(testDispatcher) {
        //Given
        whenever(movieDbRepository.getPlayingNowMovies()).thenReturn(flowOf(ApiError(movieErrorItem)))

        //When
        val result = movieDbUseCase.getPlayingNowMovies().first()

        //Then
        assertThat(result, IsInstanceOf(ApiError::class.java))
        assertThat((result as ApiError<*>).body, Is(equalTo(movieErrorItem)))
    }

    @Test
    fun `Given playing now movies return NetworkError, when movie detail is called, then show error`() = runBlockingTest(testDispatcher) {
        //Given
        whenever(movieDbRepository.getPlayingNowMovies()).thenReturn(flowOf(NetworkError(ioException)))

        //When
        val result = movieDbUseCase.getPlayingNowMovies().first()

        //Then
        assertThat(result, IsInstanceOf(NetworkError::class.java))
        assertThat((result as NetworkError).error, Is(equalTo(ioException)))
    }

    @Test
    fun `Given playing now movies return UnknownError, when playing now movies is called, then show error`() = runBlockingTest(testDispatcher) {
        //Given
        whenever(movieDbRepository.getPlayingNowMovies()).thenReturn(flowOf(UnknownError(throwable)))

        //When
        val result = movieDbUseCase.getPlayingNowMovies().first()

        //Then
        assertThat(result, IsInstanceOf(UnknownError::class.java))
        assertThat((result as UnknownError).error, Is(equalTo(throwable)))
    }
}
