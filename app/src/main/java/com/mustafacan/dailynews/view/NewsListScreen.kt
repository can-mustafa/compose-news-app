package com.mustafacan.dailynews.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
                color = Color.Cyan,
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
            Divider(color = Color.LightGray)
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(news.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = news.title,
                    modifier = Modifier
                        .size(200.dp, 200.dp)
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Column {
                    Text(
                        text = "${news.title}",
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = "Author: ${news.author}")
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(text = "Date: ${news.date}")
                }
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = "${news.content}")
            Spacer(modifier = Modifier.padding(8.dp))
            Row(modifier = Modifier.align(Alignment.End)) {
                HyperlinkText(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    fullText = "Read more",
                    linkText = listOf("Read more"),
                    hyperlinks = listOf("${news.readMoreUrl}"),
                    fontSize = 14.sp
                )
            }
        }
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

@Composable
fun HyperlinkText(
    modifier: Modifier = Modifier,
    fullText: String,
    linkText: List<String>,
    linkTextColor: Color = Color.Blue,
    linkTextFontWeight: FontWeight = FontWeight.Light,
    linkTextDecoration: TextDecoration = TextDecoration.Underline,
    hyperlinks: List<String> = listOf(""),
    fontSize: TextUnit = TextUnit.Unspecified
) {
    val annotatedString = buildAnnotatedString {
        append(fullText)
        linkText.forEachIndexed { index, link ->
            val startIndex = fullText.indexOf(link)
            val endIndex = startIndex + link.length
            addStyle(
                style = SpanStyle(
                    color = linkTextColor,
                    fontSize = fontSize,
                    fontWeight = linkTextFontWeight,
                    textDecoration = linkTextDecoration
                ),
                start = startIndex,
                end = endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = hyperlinks[index],
                start = startIndex,
                end = endIndex
            )
        }
        addStyle(
            style = SpanStyle(
                fontSize = fontSize
            ),
            start = 0,
            end = fullText.length
        )
    }

    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = {
            try {
                annotatedString
                    .getStringAnnotations("URL", it, it)
                    .firstOrNull()?.let { stringAnnotation ->
                        uriHandler.openUri(stringAnnotation.item)
                    } ?: run {
                    mToast(context)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    )
}

private fun mToast(context: Context) {
    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show()
}