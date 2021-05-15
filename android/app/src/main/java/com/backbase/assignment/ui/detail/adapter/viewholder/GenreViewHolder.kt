package com.backbase.assignment.ui.detail.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.databinding.ItemMovieGenreBinding
import com.flagos.domain.detail.model.GenreItem

class GenreViewHolder(private val binding: ItemMovieGenreBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: GenreItem) {
        binding.textGenre.text = item.name
    }
}
