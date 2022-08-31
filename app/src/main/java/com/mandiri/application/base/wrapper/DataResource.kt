package com.mandiri.news.app.base.model

import java.lang.Exception

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
sealed class DataResource<T>(
    val data: T? = null,
    val message: String? = null,
    val exception: Exception? = null,
) {
    class Success<T>(data: T) : DataResource<T>(data)
    class Error<T>(exception: Exception?, data: T? = null) : DataResource<T>(data, exception = exception)
}