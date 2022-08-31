package com.mandiri.news.app.base.model

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null, ) : Resource<T>(data, message)
}