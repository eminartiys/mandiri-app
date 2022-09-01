package com.mandiri.application.feature.reviewsbymovie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.mandiri.application.R
import com.mandiri.application.base.wrapper.ViewResource
import com.mandiri.application.data.model.response.Review
import com.mandiri.application.databinding.ActivityReviewsByMovieBinding
import com.mandiri.application.feature.review.ReviewDetailActivity
import com.mandiri.application.ui.adapter.ReviewAdapter
import com.mandiri.application.ui.viewhelper.LoadMoreOnScrollListener
import com.mandiri.news.app.base.arch.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReviewsByMovieActivity : BaseActivity<ActivityReviewsByMovieBinding>(
    ActivityReviewsByMovieBinding::inflate
) {

    private lateinit var adapter: ReviewAdapter
    private var movieId = ""
    private var movieName = ""
    private var currentPage = 1
    private var isLoading = false

    @Inject
    lateinit var viewModel: ReviewsByMovieViewModel

    private val loadMoreScrollListener = object : LoadMoreOnScrollListener(PAGE_MULTIPLIER) {
        override fun loadMore() {
            if (!isLoading) {
                getData()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    override fun configureView() {
        getIntentData(intent.extras)
        setupToolbar()
        setupRecyclerView()
        setupSwipeRefresh()
    }

    override fun showLoading(isVisible: Boolean) {
        getViewBinding().uiViewLoadingProgressbar.isVisible = isVisible
    }

    override fun showContent(isVisible: Boolean) {
        getViewBinding().uiViewReviewRecyclerview.isVisible = isVisible
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        getViewBinding().uiViewErrorContainer.isVisible = isErrorEnabled
        getViewBinding().uiViewErrorMsgTextview.text = msg
    }

    override fun observeData() {
        viewModel.movieReviewsResult.observe(this) {
            when (it) {
                is ViewResource.Loading -> {
                    if (currentPage == 1) {
                        showLoading(true)
                        showContent(false)
                        showError(false, null)
                    }
                }
                is ViewResource.Success -> {
                    if (currentPage == 1) {
                        showLoading(false)
                        showContent(true)
                        showError(false, null)
                    }
                    setDataAdapter(it.data)
                }
                is ViewResource.Error -> {
                    if (currentPage == 1) {
                        showLoading(false)
                        showContent(false)
                        showError(true, it.message)
                    }
                }
                else -> {
                    // do nothing
                }
            }
        }
    }

    private fun getData() {
        isLoading = true
        viewModel.getMovieReviews(movieId, currentPage)
    }

    private fun getIntentData(extras: Bundle?) {
        movieId = extras?.getString(EXTRAS_MOVIE_ID).orEmpty()
        movieName = extras?.getString(EXTRAS_MOVIE_NAME).orEmpty()
    }

    private fun setupRecyclerView() {
        adapter = ReviewAdapter {
            ReviewDetailActivity.startActivity(this, it.url.orEmpty())
        }
        getViewBinding().uiViewReviewRecyclerview.apply {
            adapter = this@ReviewsByMovieActivity.adapter
            val mLayoutManager = LinearLayoutManager(this@ReviewsByMovieActivity)
            layoutManager = mLayoutManager
            addOnScrollListener(loadMoreScrollListener)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(getViewBinding().toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getViewBinding().uiViewReviewTitleTextview.text =
            resources.getString(R.string.reviews_for, movieName)
    }

    private fun setupSwipeRefresh() {
        getViewBinding().uiViewSwipeRefreshContainer.setOnRefreshListener {
            currentPage = 1
            getData()
        }
    }

    private fun setDataAdapter(data: List<Review>?) {
        data?.let {
            if (currentPage == 1) {
                adapter.setItems(it)
            } else {
                adapter.addItems(it)
            }
            getViewBinding().uiViewSwipeRefreshContainer.isRefreshing = false
            isLoading = false
            currentPage++
        }
    }

    companion object {
        const val EXTRAS_MOVIE_ID = "EXTRAS_MOVIE_ID"
        const val EXTRAS_MOVIE_NAME = "EXTRAS_MOVIE_NAME"

        @JvmStatic
        fun startActivity(context: Context?, movieId: String, movieName: String) {
            val intent = Intent(context, ReviewsByMovieActivity::class.java)
            intent.putExtras(bundleOf().apply {
                putString(EXTRAS_MOVIE_ID, movieId)
                putString(EXTRAS_MOVIE_NAME, movieName)
            })
            context?.startActivity(intent)
        }
    }
}