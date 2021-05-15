package com.flagos.data.mapper

import com.flagos.data.model.MovieDetailResponse
import com.flagos.data.model.ResultsResponse
import com.flagos.data.utils.NORMAL_DATE
import com.flagos.data.utils.UI_DATE
import com.flagos.data.utils.getFormattedDate
import com.flagos.domain.detail.model.GenreItem
import com.flagos.domain.detail.model.MovieDetailItem
import com.flagos.domain.home.model.NowPlayingItem

class MoviesDataMapper {

    fun toMovieDetailItem(movieDetailResponse: MovieDetailResponse): MovieDetailItem {
        with(movieDetailResponse) {
            val formattedReleaseDate = getFormattedDate(releaseDate, NORMAL_DATE, UI_DATE)
            val genres = mutableListOf<GenreItem>().apply { genres.forEach { add(GenreItem(it.id, it.name)) } }
            return MovieDetailItem(posterPath, title, formattedReleaseDate, overview, genres)
        }
    }

    fun toNowPlayingItemList(results: List<ResultsResponse>): List<NowPlayingItem> {
        return mutableListOf<NowPlayingItem>().apply {
            results.forEach {
                add(NowPlayingItem(it.posterPath, it.id))
            }
        }
    }
}
