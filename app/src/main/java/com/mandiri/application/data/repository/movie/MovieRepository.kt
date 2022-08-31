package com.mandiri.application.data.repository.movie

import com.mandiri.application.base.arch.BaseRepositoryImpl
import com.mandiri.application.data.model.response.MovieResponse
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
}

interface MovieRepository : BaseContract.BaseRepository {
    suspend fun discoverMovieBy(genre: String, page: Int?): Flow<DataResource<MovieResponse>>
}