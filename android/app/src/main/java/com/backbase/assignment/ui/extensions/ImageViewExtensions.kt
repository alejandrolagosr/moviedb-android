package com.backbase.assignment.ui.extensions

import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.backbase.assignment.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.facebook.shimmer.ShimmerFrameLayout

private const val NO_RESOURCE_ID = 0
private const val SHIMMER_RUNNABLE_DELAY = 50L

fun ImageView.loadImageFromUrl(url: String) {
    val grayResId = ContextCompat.getDrawable(context, R.color.gray)
    Glide.with(this)
        .load(url)
        .placeholder(grayResId)
        .error(grayResId)
        .into(this)
}

fun ImageView.loadImageFromUrlWithShimmering(url: String, shimmer: ShimmerFrameLayout) {
    Glide.with(context)
        .load(url)
        .placeholder(R.color.white_alpha_60_percent)
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(NO_RESOURCE_ID)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                Handler(Looper.getMainLooper()).postDelayed({
                    shimmer.apply {
                        visibility = View.GONE
                        stopShimmer()
                    }
                }, SHIMMER_RUNNABLE_DELAY)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                Handler(Looper.getMainLooper()).postDelayed({
                    shimmer.apply {
                        visibility = View.GONE
                        stopShimmer()
                    }
                }, SHIMMER_RUNNABLE_DELAY)
                return false
            }
        })
        .centerCrop()
        .into(this)
}
