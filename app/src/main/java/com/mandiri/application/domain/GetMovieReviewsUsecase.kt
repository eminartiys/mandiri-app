package com.mandiri.application.domain

import com.mandiri.application.base.arch.BaseUseCase
import com.mandiri.application.base.wrapper.ViewResource
import com.mandiri.application.data.model.response.Movie
import com.mandiri.application.data.model.response.Review
import com.mandiri.application.data.repository.movie.MovieRepository
import com.mandiri.news.app.base.model.DataResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Created by Eminarti Sianturi on 01/09/22
 */
class GetMovieReviewsUsecase @Inject constructor(
    private val repository: MovieRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<Pair<String, Int>, List<Review>>(dispatcher) {

    override suspend fun execute(param: Pair<String, Int>?): Flow<ViewResource<List<Review>>> {
        return repository.getMovieReviews(param?.first.orEmpty(), param?.second).map {
            when (it) {
                is DataResource.Success -> {
                    if (it.data == null) {
                        ViewResource.Empty(listOf())
                    } else if (it.data.reviews.isNullOrEmpty()) {
                        ViewResource.Empty(listOf())
                    } else {
                        ViewResource.Success(it.data.reviews!!)
                    }
                }
                is DataResource.Error -> {
                    ViewResource.Error(it.exception)
                }
            }
        }.onStart { emit(ViewResource.Loading()) }
    }
}