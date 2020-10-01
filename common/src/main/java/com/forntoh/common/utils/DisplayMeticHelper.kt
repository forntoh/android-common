package com.forntoh.common.utils

import android.app.Activity
import android.content.res.Resources
import android.util.TypedValue

val Int.inPx: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.inSp: Float get() = (this * Resources.getSystem().displayMetrics.scaledDensity)

val screenWidth: Int get() = Resources.getSystem().displayMetrics.widthPixels

val Activity.statusBarHeight: Int
    get() {
        var result = 0
        val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) result = resources.getDimensionPixelSize(resourceId)
        return result
    }

val Activity.toolbarHeight: Int
    get() {
        val tv = TypedValue()
        return if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
        } else 56.inPx
    }