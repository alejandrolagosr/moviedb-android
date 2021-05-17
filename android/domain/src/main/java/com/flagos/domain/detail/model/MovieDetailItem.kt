package com.flagos.domain.detail.model

data class MovieDetailItem(
    val poster_path: String,
    val title: String,
    val release_date: String,
    val overview: String,
    val genres: List<GenreItem>
)
