package com.johnny.fagundes.spaceexplorer.utils

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageUtils {
    fun loadImage(context: Context, imageUrl: String, imageView: ImageView) {
        Glide.with(context)
            .load(imageUrl)
            .override(500, 500)
            .into(imageView)
    }
    @JvmStatic
    @BindingAdapter("loadImageUrl")
    fun loadImageUrl(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView)
            .load(imageUrl)
            .override(500, 500)
            .into(imageView)
    }
}