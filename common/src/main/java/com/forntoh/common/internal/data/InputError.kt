@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.forntoh.common.internal.data

import androidx.annotation.IdRes

object InputError {

    var view: Int = 0
    var message: Array<out Any?>? = null
    var shouldFocus = true

    fun view(@IdRes view: Int): InputError {
        InputError.view = view
        message = null
        shouldFocus = true
        return this
    }

    fun message(vararg message: Any?): InputError {
        InputError.message = message
        return this
    }

    fun noFocus(): InputError {
        shouldFocus = false
        return this
    }
}