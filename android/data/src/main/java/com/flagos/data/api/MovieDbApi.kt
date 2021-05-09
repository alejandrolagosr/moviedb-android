package com.flagos.data.api

import com.flagos.data.model.NowPlayingData
import retrofit2.http.GET

private const val DEFAULT_LANGUAGE = "en-US"
private const val NO_PAGING = "undefined"
private const val API_KEY = "9ec31697073a095bcd0e7105b0830221"

interface MovieDbApi {

    @GET("movie/now_playing?language=${DEFAULT_LANGUAGE}&page=${NO_PAGING}&api_key=${API_KEY}")
    suspend fun getNowPlayingMovies(): NowPlayingData
}
