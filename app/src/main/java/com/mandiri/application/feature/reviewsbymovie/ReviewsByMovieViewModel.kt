package com.mandiri.application.feature.reviewsbymovie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mandiri.application.base.wrapper.ViewResource
import com.mandiri.application.data.model.response.Review
import com.mandiri.application.domain.GetMovieReviewsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Eminarti Sianturi on 01/09/22
 */
@HiltViewModel
class ReviewsByMovieViewModel @Inject constructor(
    private val getMovieReviewsUsecase: GetMovieReviewsUsecase
) : ViewModel() {

    val movieReviewsResult = MutableLiveData<ViewResource<List<Review>>>()

    fun getMovieReviews(genreId: String, page: Int) {
        viewModelScope.launch {
            getMovieReviewsUsecase(Pair(genreId, page)).collect {
                movieReviewsResult.value = it
            }
        }
    }

}