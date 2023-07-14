package com.example.newsapp.ui.theme

import com.example.newsapp.R


data class NewsData(
    val id: Int,
    val image: Int = R.drawable.news_sample_4,
    val author: String,
    val title: String,
    val description: String,
    val publishedAt: String
)