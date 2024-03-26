package com.example.provatecnica

import android.util.Log
import android.view.Menu
import com.example.provatecnica.model.DataNews
import com.example.provatecnica.model.News
import com.example.provatecnica.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Gestione della chiamata API
class NewsRepository(private val newsApi: NewsApi) {
    suspend fun getNews(): List<DataNews> {
        return  withContext(Dispatchers.IO) {
            val token = Constants.API_TOKEN
            val response: Response<News> = newsApi.getNews(token).execute()
            if (response.isSuccessful) {
                Log.d("ERROR", response.toString())
                val news: News? = response.body()
                news?.data ?: emptyList()
            } else {
                throw Exception("Failed to fetch news")
            }
        }
    }
}