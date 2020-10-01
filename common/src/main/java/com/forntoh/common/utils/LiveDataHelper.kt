package com.forntoh.common.utils

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.forntoh.common.internal.data.Response
import com.forntoh.common.ui.ScopedFragment
import com.tripl3dev.prettystates.StatesConstants
import com.tripl3dev.prettystates.setState

fun <T> View.loadLiveData(liveData: LiveData<T>, lifecycleOwner: LifecycleOwner, afterDataLoaded: (T) -> Unit) {
    loadLiveData(liveData, lifecycleOwner, null, afterDataLoaded)
}

fun <T> View.loadLiveData(liveData: LiveData<T>, lifecycleOwner: LifecycleOwner, swipeRefreshLayout: SwipeRefreshLayout? = null, afterDataLoaded: (T) -> Unit) {
    setState(StatesConstants.LOADING_STATE)
    liveData.observe(lifecycleOwner, { data ->
        swipeRefreshLayout?.isRefreshing = false
        if (data != null) afterDataLoaded.invoke(data)
        val isEmpty = when (data) {
            is Collection<*> -> data.isNullOrEmpty()
            is HashMap<*, *> -> data.isNullOrEmpty()
            else -> false
        }
        setState(if (isEmpty) StatesConstants.EMPTY_STATE else StatesConstants.NORMAL_STATE)
    })
}

fun observeMultiple(vararg liveData: LiveData<*>, action: (() -> Unit)) {
    liveData.forEach {
        it.observeForever { value -> value?.let { action.invoke() } }
    }
}

fun LiveData<Response>.observeNew(fragment: ScopedFragment, lifecycleOwner: LifecycleOwner, onSuccess: () -> Unit) {
    removeObservers(lifecycleOwner)
    observe(lifecycleOwner, {
        it?.let { response ->
            if (response.type != Response.ResponseType.PENDING)
                response.message?.let { message -> fragment.root.showSnackBar(fragment.getString(message)) }
            when (response.type) {
                Response.ResponseType.PENDING -> fragment.dialog.show()
                Response.ResponseType.SUCCESS -> {
                    fragment.dialog.hide()
                    onSuccess.invoke()
                }
                else -> fragment.dialog.hide()
            }
        }
    })
}