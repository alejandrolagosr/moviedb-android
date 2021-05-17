package com.flagos.framework.home.mapper

import com.flagos.data.model.ResultsResponse
import com.flagos.data.utils.NORMAL_DATE
import com.flagos.data.utils.UI_DATE
import com.flagos.data.utils.getFormattedDate
import com.flagos.framework.home.model.MostPopularMovieItem

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

    companion object {
        const val BLANK = ""
        const val DOT = "."
    }
}
