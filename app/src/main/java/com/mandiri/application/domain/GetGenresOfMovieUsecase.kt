package com.mandiri.application.domain

import com.mandiri.application.base.arch.BaseUseCase
import com.mandiri.application.base.wrapper.ViewResource
import com.mandiri.application.data.model.response.Genre
import com.mandiri.application.data.repository.genre.GenreRepository
import com.mandiri.news.app.base.model.DataResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
class GetGenresOfMovieUsecase @Inject constructor(
    private val repository: GenreRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<Any, List<Genre>>(dispatcher) {

    override suspend fun execute(param: Any?): Flow<ViewResource<List<Genre>>> {
        return repository.getGenresOfMovie().map {
            when (it) {
                is DataResource.Success -> {
                    if (it.data == null) {
                        ViewResource.Empty(listOf())
                    } else if (it.data.genres.isNullOrEmpty()) {
                        ViewResource.Empty(listOf())
                    } else {
                        ViewResource.Success(it.data.genres!!)
                    }
                }
                is DataResource.Error -> {
                    ViewResource.Error(it.exception)
                }
            }
        }.onStart { emit(ViewResource.Loading()) }
    }
}