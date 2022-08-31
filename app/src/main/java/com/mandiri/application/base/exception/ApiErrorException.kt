package com.mandiri.application.base.exception

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
class ApiErrorException(override val message: String? = null, val httpCode: Int? = null) : Exception()