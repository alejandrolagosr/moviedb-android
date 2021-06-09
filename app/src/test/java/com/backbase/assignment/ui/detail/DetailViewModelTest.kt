package com.backbase.assignment.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.backbase.assignment.MainCoroutineRule
import com.backbase.assignment.ui.detail.DetailViewModel.MovieDetailUiState.OnShowError
import com.flagos.domain.detail.model.MovieDetailItem
import com.flagos.domain.detail.model.MovieErrorItem
import com.flagos.domain.retrofit.NetworkResponse.*
import com.flagos.domain.usecase.MovieDbUseCase
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
class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val movieDbUseCase = mock(MovieDbUseCase::class.java)
    private val movieDetailItem = mock(MovieDetailItem::class.java)
    private val movieErrorItem = mock(MovieErrorItem::class.java)
    private val ioException = mock(IOException::class.java)
    private val throwable = mock(Throwable::class.java)

    private val movieId = 1324
    private val errorText = "Error!"

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel(movieId, movieDbUseCase)
    }

    @Test
    fun `Given trying to fetch movie detail, when returns Success, then show normal ui`() {
        //Given
        whenever(movieDbUseCase.getMovieDetail(movieId)) doReturn flowOf(Success(movieDetailItem))

        //When
        detailViewModel.fetchDetail()

        //Then
        assertThat(detailViewModel.onShowLoader.value, IsEqual(false))
        assertThat(detailViewModel.onMovieDetailRetrieved.value, IsEqual(DetailViewModel.MovieDetailUiState.OnShowDetail(movieDetailItem)))
    }

    @Test
    fun `Given trying to fetch movie detail, when returns ApiError, then show error ui`() {
        //Given
        whenever(movieDbUseCase.getMovieDetail(movieId)) doReturn flowOf(ApiError(movieErrorItem))
        movieErrorItem.stub {
            on { status_message } doReturn errorText
        }

        //When
        detailViewModel.fetchDetail()

        //Then
        assertThat(detailViewModel.onShowLoader.value, IsEqual(false))
        assertThat(detailViewModel.onMovieDetailRetrieved.value, IsEqual(OnShowError(errorText)))
    }

    @Test
    fun `Given trying to fetch movie detail, when returns NetworkError, then show error ui`() {
        //Given
        whenever(movieDbUseCase.getMovieDetail(movieId)) doReturn flowOf(NetworkError(ioException))
        ioException.stub {
            on { message } doReturn errorText
        }

        //When
        detailViewModel.fetchDetail()

        //Then
        assertThat(detailViewModel.onShowLoader.value, IsEqual(false))
        assertThat(detailViewModel.onMovieDetailRetrieved.value, IsEqual(OnShowError(errorText)))
    }

    @Test
    fun `Given trying to fetch movie detail, when returns UnknownError, then show error ui`() {
        //Given
        whenever(movieDbUseCase.getMovieDetail(movieId)) doReturn flowOf(UnknownError(throwable))
        throwable.stub {
            on { message } doReturn errorText
        }

        //When
        detailViewModel.fetchDetail()

        //Then
        assertThat(detailViewModel.onShowLoader.value, IsEqual(false))
        assertThat(detailViewModel.onMovieDetailRetrieved.value, IsEqual(OnShowError(errorText)))
    }
}
