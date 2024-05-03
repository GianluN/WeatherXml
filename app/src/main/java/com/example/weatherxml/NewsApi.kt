package com.example.weatherxml

import com.example.weatherxml.model.News
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("news/all")
    fun getNews(@Query("api_token") apiKey: String): retrofit2.Call<News>
}