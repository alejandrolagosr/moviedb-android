package com.backbase.assignment.ui.home.adapter.viewholder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.databinding.ItemMovieMostPopularBinding
import com.backbase.assignment.ui.extensions.loadImageFromUrl
import com.backbase.assignment.ui.home.HomeViewModel.Companion.POSTER_PATH
import com.flagos.framework.home.model.MostPopularItem

private const val PERCENTAGE = "%"

private const val NO_RESOURCE_ID = 0
private const val RATING_GOOD_VALUE = 50

class MostPopularViewHolder(
    private val binding: ItemMovieMostPopularBinding,
    private val onMovieClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var currentItem: MostPopularItem
    private var greenColor: Int = NO_RESOURCE_ID
    private var yellowColor: Int = NO_RESOURCE_ID

    init {
        val context = binding.root.context
        greenColor = ContextCompat.getColor(context, R.color.green)
        yellowColor = ContextCompat.getColor(context, R.color.yellow)
        binding.root.setOnClickListener { onMovieClicked.invoke(currentItem.id) }
    }

    fun bind(item: MostPopularItem) {
        currentItem = item
        with(binding) {
            val rating = item.rating
            imageMovie.loadImageFromUrl(POSTER_PATH.plus(item.posterPath))
            textMovieTitle.text = item.title
            textMovieReleaseDate.text = item.releaseDate
            textMovieRating.text = rating.toString().plus(PERCENTAGE)
            progressMovieRating.apply {
                progress = rating
                setIndicatorColor(if (rating > RATING_GOOD_VALUE) greenColor else yellowColor)
            }
        }
    }
}
