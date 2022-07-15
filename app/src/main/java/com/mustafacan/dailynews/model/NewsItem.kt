package com.mustafacan.dailynews.model

data class NewsItem(
    val author: String,
    val content: String,
    val date: String,
    val id: String,
    val imageUrl: String,
    val readMoreUrl: String,
    val time: String,
    val title: String,
    val url: String
) {
    constructor(newsItem: NewsItem) : this(
        newsItem.author,
        newsItem.content,
        newsItem.date,
        newsItem.id,
        newsItem.imageUrl,
        newsItem.readMoreUrl,
        newsItem.time,
        newsItem.title,
        newsItem.url
    )
}