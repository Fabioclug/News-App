package com.fclug.newsapp.util

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fclug.newsapp.R

fun ImageView.loadImage(imageUrl: String?) {
    if (imageUrl.isNullOrBlank()) {
        visibility = View.GONE
        return
    }
    Glide.with(this)
        .load(imageUrl)
        .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
        .into(this)
}