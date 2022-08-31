package com.mandiri.application.data.model.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
data class Movie(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("imdb_id")
    var imdbId: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("tagline")
    var tagline: String? = null,
    @SerializedName("original_title")
    var original_title: String? = null,
    @SerializedName("overview")
    var overview: String? = null,
    @SerializedName("release_date")
    var release_date: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("poster_path")
    var poster_path: String? = null,
    @SerializedName("popularity")
    var popularity: String? = null,
    @SerializedName("vote_average")
    var vote_average: String? = null,
    @SerializedName("vote_count")
    var vote_count: String? = null,
    @SerializedName("production_companies")
    var productionCompanies: List<ProductionCompany>? = null,
    @SerializedName("production_countries")
    var productionCountries: List<ProductionCountry>? = null,
    @SerializedName("genres")
    var genres: List<Genre>? = null
)

data class MovieResponse(
    @SerializedName("page")
    var page: String? = null,
    @SerializedName("results")
    var movies: List<Movie>? = null,
    @SerializedName("total_pages")
    var totalPages: Int = 0,
    @SerializedName("total_results")
    var totalResults: Int = 0
)

