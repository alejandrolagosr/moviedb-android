package com.flagos.framework.home.mapper

import com.flagos.common.UI_DATE
import com.flagos.common.NORMAL_DATE
import com.flagos.common.getFormattedDate
import com.flagos.data.model.Results
import com.flagos.framework.home.model.MostPopularItem
import com.flagos.framework.home.model.NowPlayingItem

private const val BLANK = ""
private const val DOT = "."

class MoviesUiMapper {

    fun toNowPlayingItemList(results: List<Results>): List<NowPlayingItem> {
        return mutableListOf<NowPlayingItem>().apply {
            results.forEach {
                add(NowPlayingItem(it.posterPath, it.id))
            }
        }
    }

    fun toMostPopularItemList(results: List<Results>): List<MostPopularItem> {
        return mutableListOf<MostPopularItem>().apply {
            results.forEach {
                val percentage = it.voteAverage.toString().replace(DOT, BLANK).toInt()
                val formattedDate = getFormattedDate(it.releaseDate, NORMAL_DATE, UI_DATE)
                add(MostPopularItem(it.id, it.posterPath, it.title, formattedDate, percentage))
            }
        }
    }
}
