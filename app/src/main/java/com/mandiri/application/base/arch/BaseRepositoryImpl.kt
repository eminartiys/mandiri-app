package com.mandiri.application.base.arch

import android.util.Log
import com.mandiri.application.base.exception.ApiErrorException
import com.mandiri.application.base.exception.NoInternetConnectionException
import com.mandiri.application.base.exception.UnexpectedApiErrorException
import com.mandiri.news.app.base.arch.BaseContract
import com.mandiri.news.app.base.model.DataResource
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
open class BaseRepositoryImpl : BaseContract.BaseRepository {
    override fun logResponse(msg: String?) {
        Log.d(BaseRepositoryImpl::class.java.simpleName, msg.orEmpty())
    }

    suspend fun <T> safeNetworkCall(apiCall: suspend () -> T): DataResource<T> {
        return try {
            DataResource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> DataResource.Error(NoInternetConnectionException())
                is HttpException -> {
                    when (val code = throwable.code()) {
                        in 300..308 -> {
                            DataResource.Error(ApiErrorException("Redirect", code))
                        }
                        in 400..451 -> {
                            DataResource.Error(ApiErrorException("Client Error", code))
                        }
                        in 500..511 -> {
                            DataResource.Error(ApiErrorException("Server Error", code))
                        }
                        else -> {
                            DataResource.Error(ApiErrorException(httpCode = code))
                        }
                    }
                }
                else -> {
                    DataResource.Error(UnexpectedApiErrorException())
                }
            }
        }
    }

    suspend fun <T> proceed(coroutine: suspend () -> T): DataResource<T> {
        return try {
            DataResource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            DataResource.Error(exception)
        }
    }
}