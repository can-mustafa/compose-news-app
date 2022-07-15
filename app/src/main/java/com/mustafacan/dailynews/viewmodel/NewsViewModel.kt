package com.mustafacan.dailynews.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafacan.dailynews.model.NewsItem
import com.mustafacan.dailynews.repository.NewsRepository
import com.mustafacan.dailynews.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    var newsList = mutableStateOf<List<NewsItem>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun getNewsList(category: String) {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getNewsList(category)
            when (result) {
                is Resource.Success -> {
                    val newsItems = result.data?.data?.mapIndexed { _, newsItem ->
                        NewsItem(newsItem)
                    } as List<NewsItem>
                    errorMessage.value = ""
                    isLoading.value = false
                    newsList.value += newsItems
                }
                is Resource.Error -> {
                    errorMessage.value = result.message ?: "Error"
                    isLoading.value = false
                }
            }
        }
    }
}