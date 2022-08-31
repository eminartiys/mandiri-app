package com.mandiri.application.data.model.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
data class Genre(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null
)

data class GenreListResponse(
    @SerializedName("genres")
    var genres: List<Genre>? = null
)