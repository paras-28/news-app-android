package com.example.newsapp.ui.theme.screen

import com.example.newsapp.MockData
import com.example.newsapp.R
import com.example.newsapp.ui.theme.NewsData

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsapp.MockData.getTimeAgo
import com.example.newsapp.network.models.TopNewsArticle
import com.skydoves.landscapist.coil.CoilImage

//Todo 4: create TopNewsArticle list variable and replace the list in items with the size instead
@Composable
fun TopNews(navController: NavController,articles:List<TopNewsArticle>) {
    Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Top News",fontWeight = FontWeight.SemiBold)
        LazyColumn{
            items(articles.size){index->
                //Todo 5: update newsData with article from each index
                TopNewsItem(article =articles[index],
                    //Todo 11 :we pass in index when a news item is clicked
                    onNewsClick = {  navController.navigate("Detail/$index")}
                )
            }
        }
    }
}

//Todo 2 update NewsData with TopNewsArticle and replace Image with CoilImage
@Composable
fun TopNewsItem(article: TopNewsArticle,onNewsClick: () -> Unit = {},) {
    Box(modifier = Modifier
        .height(200.dp)
        .padding(8.dp).clickable {
            onNewsClick()
        }) {
        CoilImage(
            imageModel = article.urlToImage,
            // Crop, Fit, Inside, FillHeight, FillWidth, None
            contentScale = ContentScale.Crop,
            error = ImageBitmap.imageResource(R.drawable.news_sample1),
            // shows a placeholder ImageBitmap when loading.
            placeHolder = ImageBitmap.imageResource(R.drawable.news_sample1)
        )
        Column(modifier = Modifier
            .wrapContentHeight()
            .padding(top = 16.dp, start = 16.dp),verticalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = MockData.stringToDate(article.publishedAt!!).getTimeAgo(),
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(100.dp))
            //Todo 8: add maxline ellipsis when there is an overflow
            Text(text = article.title!!, color = Color.White, fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }}
}

//Todo 3:Update preview to use TopNewsItem and remove id
@Preview(showBackground = true)
@Composable
fun TopNewsPreview() {
    TopNewsItem(  TopNewsArticle(
        author = "Namita Singh",
        title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
        description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
        publishedAt = "2021-11-04T04:42:40Z"
    ))
}