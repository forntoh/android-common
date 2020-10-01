package com.forntoh.common.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest

fun ImageView.load(imageLoader: ImageLoader, request: ImageRequest.Builder, url: String) {
    imageLoader.enqueue(request.data(url).memoryCachePolicy(CachePolicy.DISABLED).target(this).build())
}

fun ImageView.load(imageLoader: ImageLoader, @DrawableRes res: Int) {
    imageLoader.enqueue(ImageRequest.Builder(context)
            .data(res)
            .target(this)
            .build())
}