package com.mandiri.application.feature.review

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import com.mandiri.application.databinding.ActivityReviewDetailBinding
import com.mandiri.news.app.base.arch.BaseActivity

class ReviewDetailActivity : BaseActivity<ActivityReviewDetailBinding>(
    ActivityReviewDetailBinding::inflate
) {

    private var reviewUrl: String = ""

    override fun configureView() {
        setupToolbar()
        getIntentData(intent.extras)
        getViewBinding().webViewContent.loadUrl(reviewUrl)
    }

    override fun observeData() {
        // do nothing
    }

    override fun showContent(isVisible: Boolean) {
        // do nothing
    }

    override fun showLoading(isVisible: Boolean) {
        // do nothing
    }

    private fun setupToolbar() {
        setSupportActionBar(getViewBinding().toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getIntentData(extras: Bundle?) {
        reviewUrl = extras?.getString(EXTRAS_REVIEW_URL).orEmpty()
    }

    companion object {
        const val EXTRAS_REVIEW_URL = "EXTRAS_REVIEW_URL"

        @JvmStatic
        fun startActivity(context: Context?, reviewUrl: String) {
            val intent = Intent(context, ReviewDetailActivity::class.java)
            intent.putExtras(bundleOf().apply {
                putString(EXTRAS_REVIEW_URL, reviewUrl)
            })
            context?.startActivity(intent)
        }
    }
}