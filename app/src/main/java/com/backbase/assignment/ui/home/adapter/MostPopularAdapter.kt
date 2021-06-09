package com.backbase.assignment.ui.home.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.databinding.ItemMovieMostPopularBinding
import com.backbase.assignment.ui.home.adapter.viewholder.MostPopularViewHolder
import com.flagos.framework.home.model.MostPopularMovieItem
import com.flagos.common.inflater

class MostPopularAdapter(private val onMovieClicked: (Int) -> Unit) :
    PagingDataAdapter<MostPopularMovieItem, RecyclerView.ViewHolder>(MostPopularItemDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = parent.inflater
        return MostPopularViewHolder(ItemMovieMostPopularBinding.inflate(inflater, parent, false), onMovieClicked)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as MostPopularViewHolder).bind(it) }
    }

    private class MostPopularItemDiff : DiffUtil.ItemCallback<MostPopularMovieItem>() {
        override fun areItemsTheSame(oldItem: MostPopularMovieItem, newItem: MostPopularMovieItem): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: MostPopularMovieItem, newItem: MostPopularMovieItem): Boolean = oldItem == newItem
    }
}
