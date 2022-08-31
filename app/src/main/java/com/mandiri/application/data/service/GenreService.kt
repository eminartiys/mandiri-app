package com.mandiri.application.data.service

import com.mandiri.application.data.model.response.Genre
import com.mandiri.application.data.model.response.GenreListResponse
import retrofit2.http.GET

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
interface GenreService {

    @GET("genre/movie/list?api_key=490c033dca0963acb96367c2a56d1a61")
    suspend fun getGenresOfMovie(): GenreListResponse

}