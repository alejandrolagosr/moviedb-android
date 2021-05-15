package com.flagos.framework.home.mapper

import com.flagos.common.UI_DATE
import com.flagos.common.NORMAL_DATE
import com.flagos.common.getFormattedDate
import com.flagos.data.model.ResultsResponse
import com.flagos.framework.home.model.MostPopularMovieItem

private const val BLANK = ""
private const val DOT = "."

class MostPopularMoviesUiMapper {

    fun toMostPopularItemList(results: List<ResultsResponse>): List<MostPopularMovieItem> {
        return mutableListOf<MostPopularMovieItem>().apply {
            results.forEach {
                val percentage = it.voteAverage.toString().replace(DOT, BLANK).toInt()
                val formattedDate = getFormattedDate(it.releaseDate, NORMAL_DATE, UI_DATE)
                add(MostPopularMovieItem(it.id, it.posterPath, it.title, formattedDate, percentage))
            }
        }
    }
}
