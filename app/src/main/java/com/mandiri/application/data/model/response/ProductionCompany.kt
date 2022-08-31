package com.mandiri.application.data.model.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Eminarti Sianturi on 31/08/22
 */
data class ProductionCompany(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("logo_path")
    var logoPath: String? = null,
    @SerializedName("origin_country")
    var originCountry: String? = null
)