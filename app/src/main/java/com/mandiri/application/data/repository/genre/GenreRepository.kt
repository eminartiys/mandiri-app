package com.mandiri.application.data.repository.genre

import com.mandiri.application.base.arch.BaseRepositoryImpl
import com.mandiri.application.data.model.response.Genre
import com.mandiri.news.app.base.arch.BaseContract
import com.mandiri.news.app.base.model.DataResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
class GenreRepositoryImpl @Inject constructor(
    private val remoteDataSource: GenreRemoteDataSource
) : BaseRepositoryImpl(), GenreRepository {

    override suspend fun getGenresOfMovie(): Flow<DataResource<List<Genre>>> =
        flow {
            emit(safeNetworkCall { remoteDataSource.getGenresOfMovie() })
        }

}

interface GenreRepository : BaseContract.BaseRepository {
    suspend fun getGenresOfMovie(): Flow<DataResource<List<Genre>>>
}