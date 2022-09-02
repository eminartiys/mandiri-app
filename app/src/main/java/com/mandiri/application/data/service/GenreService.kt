package com.mandiri.application.data.service

import com.mandiri.application.Keys
import com.mandiri.application.data.model.response.Genre
import com.mandiri.application.data.model.response.GenreListResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
interface GenreService {

    @GET("genre/movie/list")
    suspend fun getGenresOfMovie(
        @Query("api_key") apiKey: String? = Keys.getMovieApiKey(),
    ): GenreListResponse

}