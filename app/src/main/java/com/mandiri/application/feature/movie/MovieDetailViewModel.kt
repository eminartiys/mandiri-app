package com.mandiri.application.feature.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.application.base.wrapper.ViewResource
import com.mandiri.application.data.model.response.Movie
import com.mandiri.application.data.model.response.Review
import com.mandiri.application.data.model.response.Video
import com.mandiri.application.domain.GetDetailMovieUsecase
import com.mandiri.application.domain.GetMovieReviewsUsecase
import com.mandiri.application.domain.GetMovieVideoUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Eminarti Sianturi on 01/09/22
 */
@HiltViewModel
class MovieDetailViewModel
@Inject constructor(
    private val getDetailMovieUsecase: GetDetailMovieUsecase,
    private val getMovieReviewsUsecase: GetMovieReviewsUsecase,
    private val getMovieVideoUsecase: GetMovieVideoUsecase
) : ViewModel() {

    val movieDetailResult = MutableLiveData<ViewResource<Movie>>()
    val movieReviewsResult = MutableLiveData<ViewResource<List<Review>>>()
    val movieVideosResult = MutableLiveData<ViewResource<Video>>()

    fun getDetailMovie(movieId: String) {
        viewModelScope.launch {
            getDetailMovieUsecase(movieId).collect {
                movieDetailResult.value = it
            }
        }
    }

    fun getMovieReviews(movieId: String) {
        viewModelScope.launch {
            getMovieReviewsUsecase(Pair(movieId, 1)).collect {
                movieReviewsResult.value = it
            }
        }
    }

    fun getMovieVideo(movieId: String) {
        viewModelScope.launch {
            getMovieVideoUsecase(movieId).collect {
                movieVideosResult.value = it
            }
        }
    }

}