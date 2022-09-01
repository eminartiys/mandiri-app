package com.mandiri.application.data.model.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Eminarti Sianturi on 01/09/22
 */
data class Review(
    @SerializedName("author")
    var author: String? = null,
    @SerializedName("author_details")
    var authorDetails: Author? = null,
    @SerializedName("content")
    var content: String? = null,
    @SerializedName("url")
    var url: String? = null
)

data class Author(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("avatar_path")
    var avatarPath: String? = null,
)

data class ReviewResponse(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("page")
    var page: Int? = null,
    @SerializedName("results")
    var reviews: List<Review>? = null
)