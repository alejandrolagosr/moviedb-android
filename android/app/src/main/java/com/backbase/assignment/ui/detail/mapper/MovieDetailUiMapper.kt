package com.backbase.assignment.ui.detail.mapper

import com.backbase.assignment.ui.detail.model.GenreItem
import com.backbase.assignment.ui.detail.model.MovieDetailItem
import com.flagos.common.UI_DATE
import com.flagos.common.NORMAL_DATE
import com.flagos.common.getFormattedDate
import com.flagos.data.model.MovieDetailData

class MovieDetailUiMapper {

    fun toMovieDetailItem(movieDetailData: MovieDetailData): MovieDetailItem {
        with(movieDetailData) {
            val formattedDate = getFormattedDate(releaseDate, NORMAL_DATE, UI_DATE)
            val genres = mutableListOf<GenreItem>().apply { genres.forEach { add(GenreItem(it.id, it.name)) } }
            return MovieDetailItem(posterPath, title, formattedDate, overview, genres)
        }
    }
}
