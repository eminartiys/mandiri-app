package com.mandiri.application.domain

import com.mandiri.application.base.arch.BaseUseCase
import com.mandiri.application.base.wrapper.ViewResource
import com.mandiri.application.data.model.response.Movie
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
class GetDetailMovieUsecase @Inject constructor(
    private val repository: MovieRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<String, Movie>(dispatcher) {

    override suspend fun execute(param: String?): Flow<ViewResource<Movie>> {
        return repository.getMovieDetail(param.orEmpty()).map {
            when (it) {
                is DataResource.Success -> {
                    if (it.data == null) {
                        ViewResource.Empty(Movie())
                    } else {
                        ViewResource.Success(it.data)
                    }
                }
                is DataResource.Error -> {
                    ViewResource.Error(it.exception)
                }
            }
        }.onStart { emit(ViewResource.Loading()) }
    }
}