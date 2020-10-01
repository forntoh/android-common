@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.forntoh.common.internal.data

import androidx.annotation.StringRes
import com.forntoh.common.R

object Response {

    var type: ResponseType = ResponseType.SUCCESS
    var message: Int? = R.string.done
    var goBack: Boolean = false

    fun success(): Response {
        clear()
        type = ResponseType.SUCCESS
        return this
    }

    fun failure(): Response {
        clear()
        type = ResponseType.FAILURE
        return this
    }

    fun pending(): Response {
        clear()
        type = ResponseType.PENDING
        return this
    }

    fun message(@StringRes message: Int = R.string.done): Response {
        Response.message = message
        return this
    }

    fun goBack(): Response {
        goBack = true
        return this
    }

    fun clear(): Response {
        type = ResponseType.EMPTY
        message = null
        goBack = false
        return this
    }

    enum class ResponseType {
        SUCCESS, FAILURE, PENDING, EMPTY
    }
}