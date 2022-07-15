package com.mustafacan.dailynews.model

data class News(
    val category: String,
    val data: List<NewsItem>,
    val success: Boolean
)