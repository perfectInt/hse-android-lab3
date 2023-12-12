package io.sultanov.newsfetching

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("/api/1/news")
    suspend fun getNews(@Query("q") q: String, @Query("apikey") apiKey: String): Response<NewsResponse>
}