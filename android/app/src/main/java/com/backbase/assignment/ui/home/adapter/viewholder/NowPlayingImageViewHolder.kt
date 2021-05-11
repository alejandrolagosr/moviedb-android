package com.backbase.assignment.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.databinding.ItemMoviePlayingNowBinding
import com.backbase.assignment.ui.extensions.loadImageFromUrl
import com.backbase.assignment.ui.home.HomeViewModel.Companion.POSTER_PATH
import com.backbase.assignment.ui.home.model.MovieImageItem

class NowPlayingImageViewHolder(
    private val binding: ItemMoviePlayingNowBinding,
    private val onMovieClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var currentItem: MovieImageItem

    init {
        binding.imagePlayingNow.setOnClickListener { onMovieClicked.invoke(currentItem.movieId) }
    }

    fun bind(item: MovieImageItem) {
        currentItem = item
        binding.imagePlayingNow.loadImageFromUrl(POSTER_PATH.plus(item.posterPath))
    }
}
