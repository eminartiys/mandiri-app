package com.mandiri.application.data.service

import com.mandiri.application.data.model.response.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
interface MovieService {

    @GET("discover/movie")
    suspend fun searchUserByUsername(
        @Query("sort_by") sortBy: String,
        @Query("with_genres") withGenres: String,
        @Query("page") page: Int? = 1
    ): MovieResponse

}