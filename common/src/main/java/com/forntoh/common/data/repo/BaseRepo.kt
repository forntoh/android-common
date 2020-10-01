package com.forntoh.common.data.repo

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.forntoh.common.R
import com.forntoh.common.internal.data.Response
import com.forntoh.common.internal.data.ResponseHolder
import com.forntoh.common.internal.data.Result
import com.forntoh.common.internal.exceptions.NoConnectivityException
import com.forntoh.common.internal.runOnIoThread
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@Suppress("unused")
abstract class BaseRepo {

    protected suspend fun <T> load(
        liveData: MutableLiveData<List<T>>,
        @StringRes errorMessage: Int,
        block: suspend () -> Result<List<T>>
    ) =
        loadBase(liveData, errorMessage, block, null, false, shouldNotify = false)

    protected suspend fun <T> loadMutable(
        liveData: MutableLiveData<MutableList<T>>,
        @StringRes errorMessage: Int,
        block: suspend () -> Result<MutableList<T>>
    ) =
        loadBase(liveData, errorMessage, block, null, false, shouldNotify = false)

    protected suspend fun <T : Any> loadSingle(
        liveData: MutableLiveData<T>,
        @StringRes errorMessage: Int,
        block: suspend () -> Result<T>,
        shouldNotify: Boolean = false
    ) =
        loadBase(liveData, errorMessage, block, null, false, shouldNotify)

    protected suspend fun post(
        @StringRes errorMessage: Int,
        block: suspend () -> Result<Any>,
        onSuccess: (() -> Unit)? = null,
        shouldReturn: Boolean = false,
        shouldNotify: Boolean = true
    ) =
        loadBase(null, errorMessage, block, onSuccess, shouldReturn, shouldNotify)

    protected suspend fun <T : Any> loadSingleAsync(block: suspend () -> Result<T>): T? {
        val result = runOnIoThread(block)
        return if (result is Result.Success) result.data else null
    }

    private suspend fun <T : Any> loadBase(
        liveData: MutableLiveData<T>?,
        errorMessage: Int,
        block: suspend () -> Result<T>,
        onSuccess: (() -> Unit)? = null,
        shouldReturn: Boolean = false,
        shouldNotify: Boolean = true
    ) {
        val result = runOnIoThread(block)

        if (result is Result.Success) {
            liveData?.postValue(result.data!!)
            onSuccess?.invoke()
            if (shouldNotify) ResponseHolder.response.postValue(
                Response.success().apply { goBack = shouldReturn })
        } else if (result is Result.Error) {
            liveData?.postValue(null)
            result.exception.printStackTrace()
            val msg = when (result.exception) {
                is NoConnectivityException -> R.string.no_internet_connection
                is SocketTimeoutException -> R.string.no_server_connection
                is UnknownHostException -> R.string.no_server_connection
                else -> errorMessage
            }
            ResponseHolder.response.postValue(Response.failure().message(msg))
        }
    }
}