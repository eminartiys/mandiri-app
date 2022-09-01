package com.mandiri.application.domain

import com.mandiri.application.base.arch.BaseUseCase
import com.mandiri.application.base.wrapper.ViewResource
import com.mandiri.application.data.model.response.Video
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
class GetMovieVideoUsecase @Inject constructor(
    private val repository: MovieRepository,
    dispatcher: CoroutineDispatcher
) : BaseUseCase<String, Video>(dispatcher) {

    override suspend fun execute(param: String?): Flow<ViewResource<Video>> {
        var video: Video? = null
        return repository.getMovieVideos(param.orEmpty()).map {
            when (it) {
                is DataResource.Success -> {
                    if (it.data == null) {
                        ViewResource.Empty(video)
                    } else if (it.data.videos.isNullOrEmpty()) {
                        ViewResource.Empty(video)
                    } else {
                        video = it.data.videos?.firstOrNull { it.site.equals("youtube", true) }
                        if (video == null) ViewResource.Empty(video)
                        else ViewResource.Success(video!!)
                    }
                }
                is DataResource.Error -> {
                    ViewResource.Error(it.exception)
                }
            }
        }.onStart { emit(ViewResource.Loading()) }
    }
}