package com.backbase.assignment.ui.extensions

import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.backbase.assignment.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun ImageView.loadImageFromUrl(url: String) {
    val grayResId = ContextCompat.getDrawable(context, R.color.gray)
    Glide.with(this)
        .load(url)
        .placeholder(grayResId)
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(grayResId)
        .into(this)
}
