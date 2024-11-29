package com.example.newsapp.data.remote.api

import com.example.newsapp.data.remote.model.NewsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {
    @GET("svc/topstories/v2/{section}.json")
    suspend fun getTopStories(
        @Path("section") section: String,
        @Query("api-key") apiKey: String
    ): retrofit2.Response<NewsResponse>
}


data class NewsResponse(val results: List<NewsDto>)

