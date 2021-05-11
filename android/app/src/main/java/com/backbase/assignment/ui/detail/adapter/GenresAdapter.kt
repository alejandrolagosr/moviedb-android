package com.backbase.assignment.ui.detail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.databinding.ItemMovieGenreBinding
import com.backbase.assignment.ui.detail.adapter.viewholder.GenreViewHolder
import com.backbase.assignment.ui.detail.model.GenreItem
import com.flagos.common.inflater

class GenresAdapter() : ListAdapter<GenreItem, RecyclerView.ViewHolder>(GenreItemDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = parent.inflater
        return GenreViewHolder(ItemMovieGenreBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GenreViewHolder).bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size

    private class GenreItemDiff : DiffUtil.ItemCallback<GenreItem>() {
        override fun areItemsTheSame(oldItem: GenreItem, newItem: GenreItem): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: GenreItem, newItem: GenreItem): Boolean = oldItem == newItem
    }
}
