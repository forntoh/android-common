package com.forntoh.common.internal

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> runOnUiThread(block: () -> T) {
    withContext(Dispatchers.Main) {
        block.invoke()
    }
}

suspend fun <T> runOnIoThread(block: suspend () -> T) = withContext(Dispatchers.IO) {
    return@withContext block.invoke()
}