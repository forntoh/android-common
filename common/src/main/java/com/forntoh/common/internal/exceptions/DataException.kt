package com.forntoh.common.internal.exceptions

import java.io.IOException

class DataException(val code: Int, message: String?) : IOException(message)