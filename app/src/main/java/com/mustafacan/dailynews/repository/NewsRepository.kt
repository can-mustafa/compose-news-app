package com.mustafacan.dailynews.repository

import com.mustafacan.dailynews.api.NewsApi
import com.mustafacan.dailynews.model.News
import com.mustafacan.dailynews.util.Resource
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: NewsApi) {

    suspend fun getNewsList(category: String): Resource<News> {
        val response = try {
            api.getNewsByCategory(category)
        } catch (e: Exception){
            return Resource.Error("Error $e")
        }
        return Resource.Success(response)
    }
}