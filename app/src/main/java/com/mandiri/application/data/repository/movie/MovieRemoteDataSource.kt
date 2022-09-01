package com.mandiri.application.data.repository.movie

import com.mandiri.application.data.model.response.Movie
import com.mandiri.application.data.model.response.MovieResponse
import com.mandiri.application.data.model.response.ReviewResponse
import com.mandiri.application.data.model.response.VideoResponse
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

    override suspend fun getMovieDetail(movieId: String): Movie {
        return service.getMovieDetail(movieId)
    }

    override suspend fun getMovieReviews(movieId: String, page: Int): ReviewResponse {
        return service.getMovieReviews(movieId, page)
    }

    override suspend fun getMovieVideos(movieId: String): VideoResponse {
        return service.getMovieVideos(movieId)
    }
}

interface MovieRemoteDataSource {
    suspend fun discoverMovieBy(genre: String, page: Int): MovieResponse
    suspend fun getMovieDetail(movieId: String): Movie
    suspend fun getMovieReviews(movieId: String, page: Int): ReviewResponse
    suspend fun getMovieVideos(movieId: String): VideoResponse
}