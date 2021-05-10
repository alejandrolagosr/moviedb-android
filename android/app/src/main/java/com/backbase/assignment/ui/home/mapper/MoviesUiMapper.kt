package com.backbase.assignment.ui.home.mapper

import com.backbase.assignment.ui.home.model.MostPopularItem
import com.backbase.assignment.ui.home.model.MovieImageItem
import com.flagos.common.MOST_POPULAR_DATE
import com.flagos.common.NORMAL_DATE
import com.flagos.common.getFormattedDate
import com.flagos.data.model.Results

class MoviesUiMapper {

    fun toNowPlayingItemList(results: List<Results>): List<MovieImageItem> {
        return mutableListOf<MovieImageItem>().apply {
            results.forEach {
                add(MovieImageItem(it.posterPath, it.id))
            }
        }
    }

    fun toMostPopularItemList(results: List<Results>): List<MostPopularItem> {
        return mutableListOf<MostPopularItem>().apply {
            results.forEach {
                val formattedDate = getFormattedDate(it.releaseDate, NORMAL_DATE, MOST_POPULAR_DATE)
                add(MostPopularItem(it.id, it.posterPath, it.title, formattedDate, it.voteAverage))
            }
        }
    }
}
