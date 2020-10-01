package com.forntoh.common.internal.exceptions

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String = "No connectivity exception"
}