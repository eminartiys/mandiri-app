package com.mandiri.application.data.model.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Eminarti Sianturi on 01/09/22
 */
data class Video(
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("key")
    var key: String? = null,
    @SerializedName("site")
    var site: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("id")
    var id: String? = null
)

data class VideoResponse(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("results")
    var videos: List<Video>? = null
)
