package com.backbase.assignment.ui.home.mapper

import com.backbase.assignment.ui.home.model.MovieImageItem
import com.flagos.data.model.Results

class MoviesUiMapper {

    fun toNowPlayingItemList(results: List<Results>): List<MovieImageItem> {
        return mutableListOf<MovieImageItem>().apply {
            results.forEach {
                add(MovieImageItem(it.posterPath, it.id))
            }
        }
    }
}
