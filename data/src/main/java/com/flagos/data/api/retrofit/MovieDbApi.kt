package com.flagos.data.api.retrofit

import com.flagos.data.model.MovieResponses
import com.flagos.domain.detail.model.MovieDetailItem
import com.flagos.domain.detail.model.MovieErrorItem
import com.flagos.domain.home.model.MoviesItem
import com.flagos.domain.retrofit.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val DEFAULT_LANGUAGE = "en-US"
private const val NO_PAGING = "undefined"
private const val API_KEY = "YOUR API KEY"

interface MovieDbApi {

    @GET("movie/now_playing?language=${DEFAULT_LANGUAGE}&page=${NO_PAGING}&api_key=${API_KEY}")
    suspend fun getNowPlayingMovies(): NetworkResponse<MoviesItem, MovieErrorItem>

    @GET("movie/popular?language=${DEFAULT_LANGUAGE}&api_key=${API_KEY}")
    suspend fun getMostPopularMovies(@Query("page") page: Int): MovieResponses

    @GET("movie/{movieId}?language=${DEFAULT_LANGUAGE}&api_key=${API_KEY}")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): NetworkResponse<MovieDetailItem, MovieErrorItem>
}
