package com.flagos.data.mapper

import com.flagos.data.model.ResultsResponse
import com.flagos.domain.home.model.NowPlayingItem

class MoviesDataMapper {

    fun toNowPlayingItemList(results: List<ResultsResponse>): List<NowPlayingItem> {
        return mutableListOf<NowPlayingItem>().apply {
            results.forEach {
                add(NowPlayingItem(it.posterPath, it.id))
            }
        }
    }
}
