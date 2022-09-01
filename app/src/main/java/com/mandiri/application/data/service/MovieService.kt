package com.mandiri.application.data.service

import com.mandiri.application.data.model.response.Movie
import com.mandiri.application.data.model.response.MovieResponse
import com.mandiri.application.data.model.response.ReviewResponse
import com.mandiri.application.data.model.response.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
interface MovieService {

    @GET("discover/movie")
    suspend fun discoverMovieBy(
        @Query("sort_by") sortBy: String,
        @Query("with_genres") withGenres: String,
        @Query("page") page: Int? = 1,
        @Query("api_key") apiKey: String? = "490c033dca0963acb96367c2a56d1a61",
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String? = "490c033dca0963acb96367c2a56d1a61",
    ): Movie

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: String,
        @Query("page") page: Int? = 1,
        @Query("api_key") apiKey: String? = "490c033dca0963acb96367c2a56d1a61",
    ): ReviewResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String? = "490c033dca0963acb96367c2a56d1a61",
    ): VideoResponse

}