package com.mandiri.application.feature.moviebygenre

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.mandiri.application.base.wrapper.ViewResource
import com.mandiri.application.data.model.response.Genre
import com.mandiri.application.data.model.response.Movie
import com.mandiri.application.databinding.ActivityMoviesByGenreBinding
import com.mandiri.application.ui.adapter.MovieAdapter
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

    @Inject
    lateinit var viewModel: MoviesByGenreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getIntentData(intent.extras)
        getViewBinding().uiViewGenreTitleTextview.text = genreName
        getData()
    }

    override fun configureView() {
        initList()
        setSupportActionBar(getViewBinding().toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showLoading(isVisible: Boolean) {
        getViewBinding().uiViewLoadingProgressbar.isVisible = isVisible
    }

    override fun showContent(isVisible: Boolean) {
        getViewBinding().uiViewGenreRecyclerview.isVisible = isVisible
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        getViewBinding().uiViewErrorContainer.isVisible = isErrorEnabled
        getViewBinding().uiViewErrorMsgTextview.text = msg
    }

    override fun observeData() {
        viewModel.discoverMovieByResult.observe(this) {
            when (it) {
                is ViewResource.Loading -> {
                    showLoading(true)
                    showContent(false)
                    showError(false, null)
                }
                is ViewResource.Success -> {
                    showLoading(false)
                    showContent(true)
                    showError(false, null)
                    setDataAdapter(it.data)
                }
                is ViewResource.Error -> {
                    showLoading(false)
                    showContent(false)
                    showError(true, it.message)
                }
            }
        }
    }

    private fun getData() {
        viewModel.discoverMovieBy(genreId, currentPage)
    }

    private fun getIntentData(extras: Bundle?) {
        genreId = extras?.getString(EXTRAS_GENRE_ID).orEmpty()
        genreName = extras?.getString(EXTRAS_GENRE_NAME).orEmpty()
    }

    private fun initList() {
        adapter = MovieAdapter {
        }
        getViewBinding().uiViewGenreRecyclerview.apply {
            adapter = this@MoviesByGenreActivity.adapter
            layoutManager = LinearLayoutManager(this@MoviesByGenreActivity)
        }
    }

    private fun setDataAdapter(data: List<Movie>?) {
        data?.let { adapter.setItems(it) }
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