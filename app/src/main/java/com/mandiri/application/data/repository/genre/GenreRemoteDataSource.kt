package com.mandiri.application.data.repository.genre

import com.mandiri.application.data.model.response.Genre
import com.mandiri.application.data.service.GenreService
import javax.inject.Inject

/**
 * Created by Eminarti Sianturi on 31/08/22
 */

class GenreRemoteDataSourceImpl
@Inject constructor(private val service: GenreService) : GenreRemoteDataSource {
    override suspend fun getGenresOfMovie(): List<Genre> = service.getGenresOfMovie()
}

interface GenreRemoteDataSource {
    suspend fun getGenresOfMovie(): List<Genre>
}