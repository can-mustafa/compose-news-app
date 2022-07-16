package com.mustafacan.dailynews.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mustafacan.dailynews.util.Constants

@Composable
fun NewsCategoryScreen(
    navController: NavController
) {
    val categoryList = Constants.NEWS_CATEGORIES
    Surface(
        color = Color.White, modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                TopAppBar {
                    Text(
                        text = "News App",
                        style = MaterialTheme.typography.h5,
                        color = Color.White
                    )
                }

                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Bold,
                    color = Color.Blue,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Column {
                    CategoryList(navController, categoryList)
                }
            }
        }
    }
}

@Composable
fun CategoryList(
    navController: NavController, categoryList: List<String>
) {

    LazyColumn(contentPadding = PaddingValues(5.dp)) {
        items(categoryList) { category ->
            CategoryRow(navController = navController, category = category)
            Divider(color = Color.LightGray)
        }
    }
}

@Composable
fun CategoryRow(
    navController: NavController, category: String
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.White)
        .clickable {
            navController.navigate("news_list_screen/$category")
        }) {
        Text(
            text = category,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
    }
}