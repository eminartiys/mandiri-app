package com.mandiri.application.feature.moviesbygenre

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.mandiri.application.base.wrapper.ViewResource
import com.mandiri.application.data.model.response.Movie
import com.mandiri.application.databinding.ActivityMoviesByGenreBinding
import com.mandiri.application.feature.movie.MovieDetailActivity
import com.mandiri.application.ui.adapter.MovieAdapter
import com.mandiri.application.ui.viewhelper.LoadMoreOnScrollListener
import com.mandiri.news.app.base.arch.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesByGenreActivity : BaseActivity<ActivityMoviesByGenreBinding>(
    ActivityMoviesByGenreBinding::inflate
) {

    private lateinit var adapter: MovieAdapter
    private var genreId = ""
    private var genreName = ""
    private var currentPage = 1
    private var isLoading = false

    @Inject
    lateinit var viewModel: MoviesByGenreViewModel

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
        getViewBinding().uiViewMovieRecyclerview.isVisible = isVisible
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        getViewBinding().uiViewErrorContainer.isVisible = isErrorEnabled
        getViewBinding().uiViewErrorMsgTextview.text = msg
    }

    override fun observeData() {
        viewModel.discoverMovieByResult.observe(this) {
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
        viewModel.discoverMovieBy(genreId, currentPage)
    }

    private fun getIntentData(extras: Bundle?) {
        genreId = extras?.getString(EXTRAS_GENRE_ID).orEmpty()
        genreName = extras?.getString(EXTRAS_GENRE_NAME).orEmpty()
    }

    private fun setupRecyclerView() {
        adapter = MovieAdapter {
            MovieDetailActivity.startActivity(this, it.id.orEmpty())
        }
        getViewBinding().uiViewMovieRecyclerview.apply {
            adapter = this@MoviesByGenreActivity.adapter
            val mLayoutManager = LinearLayoutManager(this@MoviesByGenreActivity)
            layoutManager = mLayoutManager
            addOnScrollListener(loadMoreScrollListener)
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(getViewBinding().toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getViewBinding().uiViewGenreTitleTextview.text = genreName
    }

    private fun setupSwipeRefresh() {
        getViewBinding().uiViewSwipeRefreshContainer.setOnRefreshListener {
            currentPage = 1
            getData()
        }
    }

    private fun setDataAdapter(data: List<Movie>?) {
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
        const val EXTRAS_GENRE_ID = "EXTRAS_GENRE_ID"
        const val EXTRAS_GENRE_NAME = "EXTRAS_GENRE_NAME"

        @JvmStatic
        fun startActivity(context: Context?, genreId: String, genreName: String) {
            val intent = Intent(context, MoviesByGenreActivity::class.java)
            intent.putExtras(bundleOf().apply {
                putString(EXTRAS_GENRE_ID, genreId)
                putString(EXTRAS_GENRE_NAME, genreName)
            })
            context?.startActivity(intent)
        }
    }
}