package com.forntoh.common.utils

import android.app.Activity
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView

fun Activity.hideKeyboard() {
    var view = currentFocus
    if (view == null) view = View(this)
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.getLoadingDialog() = MaterialDialog(this).show {
    customView(view = ProgressBar(context).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            indeterminateDrawable.colorFilter = BlendModeColorFilter(Color.WHITE, BlendMode.MULTIPLY)
        } else indeterminateDrawable.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY)
    })
    cancelOnTouchOutside(false)
    this.view.background = ColorDrawable(Color.TRANSPARENT)
}

