package com.example.provatecnica.model

import com.google.gson.annotations.SerializedName

class Meta (
    @SerializedName("found") val found: Int,
    @SerializedName("returned") val returned: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("page") val page: Int
)
