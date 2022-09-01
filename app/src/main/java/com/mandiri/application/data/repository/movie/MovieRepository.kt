package com.mandiri.application.data.repository.movie

import com.mandiri.application.base.arch.BaseRepositoryImpl
import com.mandiri.application.data.model.response.Movie
import com.mandiri.application.data.model.response.MovieResponse
import com.mandiri.application.data.model.response.ReviewResponse
import com.mandiri.application.data.model.response.VideoResponse
import com.mandiri.news.app.base.arch.BaseContract
import com.mandiri.news.app.base.model.DataResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
) : BaseRepositoryImpl(), MovieRepository {


    override suspend fun discoverMovieBy(
        genre: String,
        page: Int?
    ): Flow<DataResource<MovieResponse>> =
        flow {
            emit(safeNetworkCall { remoteDataSource.discoverMovieBy(genre, page ?: 1) })
        }

    override suspend fun getMovieDetail(movieId: String): Flow<DataResource<Movie>> =
        flow {
            emit(safeNetworkCall { remoteDataSource.getMovieDetail(movieId) })
        }


    override suspend fun getMovieReviews(
        movieId: String,
        page: Int?
    ): Flow<DataResource<ReviewResponse>> =
        flow {
            emit(safeNetworkCall { remoteDataSource.getMovieReviews(movieId, page ?: 1) })
        }


    override suspend fun getMovieVideos(movieId: String): Flow<DataResource<VideoResponse>> =
        flow {
            emit(safeNetworkCall { remoteDataSource.getMovieVideos(movieId) })
        }

}

interface MovieRepository : BaseContract.BaseRepository {
    suspend fun discoverMovieBy(genre: String, page: Int?): Flow<DataResource<MovieResponse>>
    suspend fun getMovieDetail(movieId: String): Flow<DataResource<Movie>>
    suspend fun getMovieReviews(movieId: String, page: Int?): Flow<DataResource<ReviewResponse>>
    suspend fun getMovieVideos(movieId: String): Flow<DataResource<VideoResponse>>
}