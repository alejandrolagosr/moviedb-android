package com.backbase.assignment.ui.home.adapter.viewholder

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.databinding.ItemMoviePlayingNowBinding
import com.backbase.assignment.ui.extensions.loadImageFromUrlWithShimmering
import com.backbase.assignment.ui.home.model.MovieImageItem

//TODO: If other endpoints get images from here, put in other side.
private const val POSTER_PATH = "https://image.tmdb.org/t/p/original/"

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
        with(binding) {
            imagePlayingNow.loadImageFromUrlWithShimmering(POSTER_PATH.plus(item.posterPath), shimmer)
            shimmer.apply {
                visibility = View.VISIBLE
                startShimmer()
            }
        }
    }
}
