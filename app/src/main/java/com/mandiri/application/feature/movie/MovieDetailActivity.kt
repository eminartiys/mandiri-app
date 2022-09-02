package com.mandiri.application.feature.movie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.mandiri.application.base.wrapper.ViewResource
import com.mandiri.application.Keys
import com.mandiri.application.data.model.response.Movie
import com.mandiri.application.data.model.response.Review
import com.mandiri.application.data.model.response.Video
import com.mandiri.application.databinding.ActivityMovieDetailBinding
import com.mandiri.application.di.NetworkModule
import com.mandiri.application.feature.moviesbygenre.MoviesByGenreActivity
import com.mandiri.application.feature.review.ReviewDetailActivity
import com.mandiri.application.feature.reviewsbymovie.ReviewsByMovieActivity
import com.mandiri.application.feature.trailer.TrailerActivity
import com.mandiri.application.ui.adapter.GenreBgAdapter
import com.mandiri.application.ui.adapter.ReviewAdapter
import com.mandiri.news.app.base.arch.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailActivity : BaseActivity<ActivityMovieDetailBinding>(
    ActivityMovieDetailBinding::inflate
) {

    private lateinit var genreAdapter: GenreBgAdapter
    private lateinit var reviewAdapter: ReviewAdapter
    private var movieId: String = ""
    private var movieName: String? = ""
    private var videoTrailer: Video? = null

    @Inject
    lateinit var viewModel: MovieDetailViewModel

    lateinit var apiKeys: Keys

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiKeys = Keys()
        getData()
    }

    override fun configureView() {
        getIntentData(intent.extras)
        setupToolbar()
        setupRecyclerView()
        setOnClick()
    }

    override fun observeData() {
        viewModel.movieDetailResult.observe(this) {
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
                    showData(it.data)
                }
                is ViewResource.Error -> {
                    showLoading(false)
                    showContent(false)
                    showError(true, it.exception?.message.orEmpty())
                }
                else -> {
                    // do nothing
                }
            }
        }

        viewModel.movieVideosResult.observe(this) {
            when (it) {
                is ViewResource.Success -> {
                    getViewBinding().uiViewPlayButton.isEnabled = true
                    videoTrailer = it.data
                }
                else -> {
                    getViewBinding().uiViewPlayButton.isEnabled = false
                }
            }
        }

        viewModel.movieReviewsResult.observe(this) {
            when (it) {
                is ViewResource.Success -> {
                    getViewBinding().uiViewMovieReviewLabelTextview.isVisible = true
                    showReviews(it.data)
                }
                else -> {
                    getViewBinding().uiViewMovieReviewLabelTextview.isVisible = false
                    getViewBinding().uiViewMovieReviewLabelSeemoreTextview.isVisible = false
                }
            }
        }
    }

    override fun showContent(isVisible: Boolean) {
        getViewBinding().uiViewMovieContainer.isVisible = isVisible
    }

    override fun showLoading(isVisible: Boolean) {
        getViewBinding().uiViewLoadingProgressbar.isVisible = isVisible
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        getViewBinding().uiViewErrorContainer.isVisible = isErrorEnabled
        getViewBinding().uiViewErrorMsgTextview.text = msg
    }

    private fun getIntentData(extras: Bundle?) {
        movieId = extras?.getString(EXTRAS_MOVIE_ID).orEmpty()
    }

    private fun setupToolbar() {
        setSupportActionBar(getViewBinding().toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupRecyclerView() {
        genreAdapter = GenreBgAdapter {
            MoviesByGenreActivity.startActivity(this, it.id.orEmpty(), it.name.orEmpty())
        }
        getViewBinding().uiViewGenreRecyclerview.apply {
            adapter = this@MovieDetailActivity.genreAdapter
            layoutManager =
                LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        reviewAdapter = ReviewAdapter {
            ReviewDetailActivity.startActivity(this, it.url.orEmpty())
        }
        getViewBinding().uiViewReviewRecyclerview.apply {
            adapter = this@MovieDetailActivity.reviewAdapter
            layoutManager = LinearLayoutManager(this@MovieDetailActivity)
        }
    }

    private fun setOnClick() {
        getViewBinding().uiViewPlayButton.setOnClickListener {
            videoTrailer?.key?.let { videoHash ->
                TrailerActivity.startActivity(this, videoHash)
            }
        }

        getViewBinding().uiViewMovieReviewLabelSeemoreTextview.setOnClickListener {
            ReviewsByMovieActivity.startActivity(this, movieId, movieName.orEmpty())
        }

        getViewBinding().uiViewRetryButton.setOnClickListener {
            getData()
        }
    }

    private fun getData() {
        viewModel.getDetailMovie(movieId)
        viewModel.getMovieReviews(movieId)
        viewModel.getMovieVideo(movieId)
    }

    private fun showData(movie: Movie?) {
        movie?.let {
            movieName = it.title
            getViewBinding().apply {
                uiViewMoviePosterImageview.load(NetworkModule.BASE_URL_PIC.plus(it.posterPath)) {
                    transformations(RoundedCornersTransformation(8f))
                }
                uiViewMovieNameTextview.text = it.title
                uiViewMovieTaglineTextview.text = it.tagline
                uiViewMovieOverviewTextview.text = it.overview
                uiViewMovieStarTextview.text = " ${it.voteCount ?: 0}"
                uiViewMovieStatusTextview.text = " • ${it.status}"
                it.releaseDate?.let { release ->
                    val cal = Calendar.getInstance()
                    cal.timeZone = TimeZone.getDefault()
                    cal.time = release

                    val sDf = SimpleDateFormat("yyyy", Locale.getDefault())
                    val dateRelease = sDf.format(cal.time)

                    uiViewMovieReleaseTextview.text = " • $dateRelease"
                }
                it.genres?.let { genres -> genreAdapter.setItems(genres) }
            }
        }
    }

    private fun showReviews(reviews: List<Review>?) {
        reviews?.let {
            if (it.size > 3) {
                reviewAdapter.setItems(it.subList(0, 3))
                getViewBinding().uiViewMovieReviewLabelSeemoreTextview.isVisible = true
            } else {
                reviewAdapter.setItems(it)
                getViewBinding().uiViewMovieReviewLabelSeemoreTextview.isVisible = false
            }
        }
    }

    companion object {
        const val EXTRAS_MOVIE_ID = "EXTRAS_MOVIE_ID"

        @JvmStatic
        fun startActivity(context: Context?, movieId: String) {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtras(bundleOf().apply {
                putString(EXTRAS_MOVIE_ID, movieId)
            })
            context?.startActivity(intent)
        }
    }
}