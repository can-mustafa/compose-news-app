package com.mustafacan.dailynews.api

import com.mustafacan.dailynews.model.News
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("news")
    suspend fun getNewsByCategory(
        @Query("category") category: String
    ) : News
}