package com.johnny.fagundes.spaceexplorer.feature.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageUtils {
    fun loadImage(context: Context, imageUrl: String, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .override(500, 500)
            .into(imageView)
    }
}