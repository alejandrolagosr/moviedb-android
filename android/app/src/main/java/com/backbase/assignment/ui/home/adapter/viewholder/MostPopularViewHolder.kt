package com.backbase.assignment.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.databinding.ItemMovieMostPopularBinding
import com.backbase.assignment.ui.extensions.loadImageFromUrl
import com.backbase.assignment.ui.home.HomeViewModel.Companion.POSTER_PATH
import com.backbase.assignment.ui.home.model.MostPopularItem

class MostPopularViewHolder(
    private val binding: ItemMovieMostPopularBinding,
    private val onMovieClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var currentItem: MostPopularItem

    init {
        binding.root.setOnClickListener { onMovieClicked.invoke(currentItem.id) }
    }

    fun bind(item: MostPopularItem) {
        currentItem = item
        with(binding) {
            imageMovie.loadImageFromUrl(POSTER_PATH.plus(item.posterPath))
            textMovieTitle.text = item.title
            textMovieReleaseDate.text = item.releaseDate
        }
    }
}