package com.backbase.assignment.ui.detail.model

data class MovieDetailItem(
    val porterPath: String,
    val title: String,
    val releaseDate: String,
    val overview: String,
    val genres: List<GenreItem>
)
