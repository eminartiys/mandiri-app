package com.mandiri.news.app.base.arch

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
interface BaseContract {

    interface BaseView {
        fun observeData()
        fun showContent(isVisible: Boolean)
        fun showLoading(isVisible: Boolean)
        fun showError(isErrorEnabled: Boolean, msg: String? = null)
    }

    interface BaseRepository {
        fun logResponse(msg: String?)
    }
}