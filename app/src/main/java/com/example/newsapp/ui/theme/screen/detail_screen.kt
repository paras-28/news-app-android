package com.example.newsapp.ui.theme.screen

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.MockData
import com.example.newsapp.MockData.getTimeAgo
import com.example.newsapp.R
import com.example.newsapp.network.models.TopNewsArticle
import com.skydoves.landscapist.coil.CoilImage

/**Todo 13: replace newsData with topNewsArticle and also replace the element values with data from it
 * Replace Image with CoilImage
 * For each Text we use elvis operator ?: to set the the value if its not null else set Not Available
 */
@Composable
fun DetailScreen(article: TopNewsArticle, scrollState: ScrollState,navController: NavController) {
    Scaffold(topBar = {
        DetailTopAppBar(onBackPressed = {navController.popBackStack()})
    }) { it
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CoilImage(
                imageModel = article.urlToImage,
                // Crop, Fit, Inside, FillHeight, FillWidth, None
                contentScale = ContentScale.Crop,
                error = ImageBitmap.imageResource(R.drawable.news_sample1),
                // shows a placeholder ImageBitmap when loading.
                placeHolder = ImageBitmap.imageResource(R.drawable.news_sample1)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoWithIcon(Icons.Default.Edit, info = article.author?:"Not Available")
                InfoWithIcon(icon = Icons.Default.DateRange, info = MockData.stringToDate(article.publishedAt!!).getTimeAgo())
            }
            Text(text = article.title?:"Not Available", fontWeight = FontWeight.Bold)
            Text(text = article.description?:"Not Available", modifier = Modifier.padding(top = 16.dp))
        }
    }
}

@Composable
fun DetailTopAppBar(onBackPressed: () -> Unit = {}) {
    TopAppBar(title = { Text(text = "Detail Screen", fontWeight = FontWeight.SemiBold) },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back")
            }
        })
}

@Composable
fun InfoWithIcon(icon: ImageVector, info: String) {
    Row {
        Icon(
            icon,
            contentDescription = "Author",
            modifier = Modifier.padding(end = 8.dp),
            colorResource(
                id = R.color.purple_500
            )
        )
        Text(text = info)
    }
}

//Todo 14: replace the preview data with TopNewsArticle
@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        TopNewsArticle(
            author = "Namita Singh",
            title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
            description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
            publishedAt = "2021-11-04T04:42:40Z"
        ), rememberScrollState(),
        rememberNavController()
    )
}