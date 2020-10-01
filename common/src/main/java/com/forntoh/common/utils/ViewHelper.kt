package com.forntoh.common.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

fun View.hide(animate: Boolean = false) {
    if (animate) {
        alpha = 1f
        animate()
                .alpha(0f)
                .setDuration(resources.getInteger(android.R.integer.config_shortAnimTime).toLong())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        visibility = View.GONE
                    }
                })
    } else visibility = View.GONE
}

fun View.show(animate: Boolean = false) {
    if (animate) {
        alpha = 0f
        visibility = View.VISIBLE
        animate()
                .alpha(1f)
                .setListener(null)
                .duration = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
    } else visibility = View.VISIBLE
}

fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT, actionTitle: String = "", action: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, message, duration)
    snackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = 3
    if (action != null) snackBar.setAction(actionTitle) { action.invoke() }.show()
    else snackBar.show()
}

fun View.showError(message: String?, shouldFocus: Boolean) {
    if (this is TextInputLayout) error = message
    else if (this is EditText) error = message
    if (shouldFocus) requestFocus()
}