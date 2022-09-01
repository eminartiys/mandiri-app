package com.mandiri.application.feature.main

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.mandiri.application.base.wrapper.ViewResource
import com.mandiri.application.data.model.response.Genre
import com.mandiri.application.databinding.ActivityMainBinding
import com.mandiri.application.feature.moviesbygenre.MoviesByGenreActivity
import com.mandiri.application.ui.adapter.GenreAdapter
import com.mandiri.news.app.base.arch.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {

    private lateinit var adapter: GenreAdapter

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    private fun getData() {
        viewModel.getGenresOfMovie()
    }

    override fun configureView() {
        initList()
        getViewBinding().uiViewRetryButton.setOnClickListener {
            getData()
        }
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
        viewModel.genresOfMovieResult.observe(this) {
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
                    showError(true, it.exception?.message.orEmpty())
                }
            }
        }

    }

    private fun initList() {
        adapter = GenreAdapter {
            MoviesByGenreActivity.startActivity(this, it.id.orEmpty(), it.name.orEmpty())
        }
        getViewBinding().uiViewGenreRecyclerview.apply {
            adapter = this@MainActivity.adapter
            layoutManager = GridLayoutManager(this@MainActivity, 2)
        }
    }

    private fun setDataAdapter(data: List<Genre>?) {
        data?.let { adapter.setItems(it) }
    }

}