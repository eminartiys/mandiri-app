package com.mandiri.application.data.repository.genre

import com.mandiri.application.data.model.response.Genre
import com.mandiri.application.data.model.response.GenreListResponse
import com.mandiri.application.data.service.GenreService
import javax.inject.Inject

/**
 * Created by Eminarti Sianturi on 31/08/22
 */

class GenreRemoteDataSourceImpl
@Inject constructor(private val service: GenreService) : GenreRemoteDataSource {
    override suspend fun getGenresOfMovie(): GenreListResponse = service.getGenresOfMovie()
}

interface GenreRemoteDataSource {
    suspend fun getGenresOfMovie(): GenreListResponse
}