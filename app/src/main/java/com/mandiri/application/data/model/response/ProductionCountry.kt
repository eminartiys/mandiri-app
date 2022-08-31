package com.mandiri.application.data.model.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
data class ProductionCountry(
    @SerializedName("iso_3166_1")
    var isoId: String? = null,
    @SerializedName("name")
    var name: String? = null
)