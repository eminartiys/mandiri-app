package com.mandiri.application.data.repository.movie

import com.mandiri.application.data.model.response.MovieResponse
import com.mandiri.application.data.service.MovieService
import javax.inject.Inject

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
class MovieRemoteDataSourceImpl
@Inject constructor(private val service: MovieService) : MovieRemoteDataSource {

    companion object {
        const val SORT_BY = "popularity.desc"
    }

    override suspend fun discoverMovieBy(genre: String, page: Int): MovieResponse {
        return service.discoverMovieBy(SORT_BY, genre, page)
    }
}

interface MovieRemoteDataSource {
    suspend fun discoverMovieBy(genre: String, page: Int): MovieResponse
}