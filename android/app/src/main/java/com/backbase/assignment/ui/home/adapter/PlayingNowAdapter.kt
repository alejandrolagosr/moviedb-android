package com.backbase.assignment.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.databinding.ItemMoviePlayingNowBinding
import com.backbase.assignment.ui.home.adapter.viewholder.NowPlayingImageViewHolder
import com.backbase.assignment.ui.home.model.MovieImageItem
import com.flagos.common.inflater

class PlayingNowAdapter(private val onMovieClicked: (Int) -> Unit) :
    ListAdapter<MovieImageItem, RecyclerView.ViewHolder>(NowPlayingItemDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = parent.inflater
        return NowPlayingImageViewHolder(
            ItemMoviePlayingNowBinding.inflate(inflater, parent, false),
            onMovieClicked
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NowPlayingImageViewHolder).bind(currentList[position])
    }

    override fun getItemCount() = currentList.size

    private class NowPlayingItemDiff : DiffUtil.ItemCallback<MovieImageItem>() {
        override fun areItemsTheSame(oldItem: MovieImageItem, newItem: MovieImageItem): Boolean = oldItem.movieId == newItem.movieId
        override fun areContentsTheSame(oldItem: MovieImageItem, newItem: MovieImageItem): Boolean = oldItem == newItem
    }
}
