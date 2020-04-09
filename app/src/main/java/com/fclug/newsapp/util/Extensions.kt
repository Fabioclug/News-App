package com.fclug.newsapp.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.fclug.newsapp.R

fun ImageView.loadImage(imageUrl: String?) {
    Glide.with(this)
        .load(imageUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .apply(RequestOptions().error(R.drawable.ic_image_placeholder).fallback(R.drawable.ic_image_placeholder))
        //.apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
        .into(this)
}

val Any.TAG: String
    get() = javaClass.simpleName