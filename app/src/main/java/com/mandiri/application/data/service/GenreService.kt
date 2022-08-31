package com.mandiri.application.data.service

import com.mandiri.application.data.model.response.Genre
import retrofit2.http.GET

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
interface GenreService {

    @GET("genre/movie/list")
    suspend fun getGenresOfMovie(): List<Genre>

}