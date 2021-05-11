package com.backbase.assignment.ui.home.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.databinding.ItemMovieMostPopularBinding
import com.backbase.assignment.ui.home.adapter.viewholder.MostPopularViewHolder
import com.backbase.assignment.ui.home.model.MostPopularItem
import com.flagos.common.inflater

class MostPopularAdapter(private val onMovieClicked: (Int) -> Unit) :
    ListAdapter<MostPopularItem, RecyclerView.ViewHolder>(MostPopularItemDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = parent.inflater
        return MostPopularViewHolder(ItemMovieMostPopularBinding.inflate(inflater, parent, false), onMovieClicked)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MostPopularViewHolder).bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size

    private class MostPopularItemDiff : DiffUtil.ItemCallback<MostPopularItem>() {
        override fun areItemsTheSame(oldItem: MostPopularItem, newItem: MostPopularItem): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: MostPopularItem, newItem: MostPopularItem): Boolean = oldItem == newItem
    }
}