package com.example.weatherxml.model

import com.google.gson.annotations.SerializedName

class News(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("data") val data: List<DataNews>

)