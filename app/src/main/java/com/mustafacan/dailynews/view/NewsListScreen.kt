package com.mustafacan.dailynews.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustafacan.dailynews.model.NewsItem
import com.mustafacan.dailynews.viewmodel.NewsViewModel

@Composable
fun NewsListScreen(
    category: String,
    viewModel: NewsViewModel = hiltViewModel()
) {
    viewModel.getNewsList(category = category)
    NewsList(category = category)
}

@Composable
fun NewsList(
    category: String,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val newsList by remember { viewModel.newsList }
    val errorMessage by remember { viewModel.errorMessage }
    val isLoading by remember { viewModel.isLoading }

    NewsListView(newsList = newsList)

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.Magenta,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        if (errorMessage.isNotEmpty()) {
            RetryView(error = errorMessage) {
                viewModel.getNewsList(category = category)
            }
        }
    }
}

@Composable
fun NewsListView(newsList: List<NewsItem>) {
    LazyColumn(contentPadding = PaddingValues(5.dp)) {
        items(newsList) { news ->
            NewsRow(news = news)
            Divider(color = Color.Black)
        }
    }
}

@Composable
fun NewsRow(news: NewsItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ) {
        Text(text = news.title ?: "assadasd")
    }
}

@Composable
fun RetryView(error: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = error, color = Color.Red, fontSize = 20.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}