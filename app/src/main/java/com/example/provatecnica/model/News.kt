package com.example.provatecnica.model

import com.example.provatecnica.model.DataNews
import com.example.provatecnica.model.Meta
import com.google.gson.annotations.SerializedName

class News(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("data") val data: List<DataNews>

)