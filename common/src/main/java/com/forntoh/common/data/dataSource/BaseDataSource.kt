package com.forntoh.common.data.dataSource

import com.forntoh.common.internal.data.Result
import com.forntoh.common.internal.exceptions.DataException
import retrofit2.Response

@Suppress("UNCHECKED_CAST", "unused")
abstract class BaseDataSource {

    protected suspend fun <T : Any> apiCover(api: suspend () -> Response<T>) =
            baseApiCover(true, api) as Result<T>

    protected suspend fun apiCoverNoData(api: suspend () -> Response<Any>) =
            baseApiCover(false, api)

    private suspend fun <T> baseApiCover(hasData: Boolean, api: suspend () -> Response<T>): Result<Any> {
        return try {
            val fetchedData = api.invoke()
            if (fetchedData.isSuccessful) if (hasData) Result.Success(fetchedData.body()) else Result.Success<Nothing>()
            else Result.Error(DataException(fetchedData.code(), fetchedData.message()))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e)
        }
    }

}