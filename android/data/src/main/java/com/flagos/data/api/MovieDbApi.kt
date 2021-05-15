package com.flagos.data.api

import com.flagos.data.model.MovieDetailResponse
import com.flagos.data.model.MovieResponses
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val DEFAULT_LANGUAGE = "en-US"
private const val NO_PAGING = "undefined"
private const val API_KEY = "9ec31697073a095bcd0e7105b0830221"

interface MovieDbApi {

    @GET("movie/now_playing?language=${DEFAULT_LANGUAGE}&page=${NO_PAGING}&api_key=${API_KEY}")
    suspend fun getNowPlayingMovies(): MovieResponses

    @GET("movie/popular?language=${DEFAULT_LANGUAGE}&api_key=${API_KEY}")
    suspend fun getMostPopularMovies(@Query("page") page: Int): MovieResponses

    @GET("movie/{movieId}?language=${DEFAULT_LANGUAGE}&api_key=${API_KEY}")
    suspend fun getMovieDetail(@Path("movieId") movieId: Int): MovieDetailResponse
}
