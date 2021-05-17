package com.flagos.domain.home.model

data class MoviesItem(val total_results: Int, val results: List<NowPlayingItem>)

data class NowPlayingItem(val poster_path: String, val id: Int)
